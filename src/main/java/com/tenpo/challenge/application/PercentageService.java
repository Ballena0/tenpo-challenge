package com.tenpo.challenge.application;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.tenpo.challenge.infrastructure.persistence.PercentageEntity;
import com.tenpo.challenge.infrastructure.persistence.PercentageJpaRepository;
import com.tenpo.challenge.interfaces.exception.PercentageException;

import java.util.logging.Logger;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class PercentageService {
    private static final String THIRD_PARTY_URL = "https://www.random.org/decimal-fractions/?num=1&dec=8&col=1&format=plain&rnd=new";
    private static final Logger logger = Logger.getLogger(PercentageService.class.getName());
    
    private final PercentageJpaRepository jpaRepository;
    private final WebClient webClient;


    public PercentageService(PercentageJpaRepository jpaRepository, WebClient webClient) {
        this.jpaRepository = jpaRepository;
        this.webClient = webClient;
    }

    public Double getValue(String key) {
        PercentageEntity entity = jpaRepository.findById(key).orElse(null);
        return entity.getValue();
    }

    @Scheduled(fixedRate = 30 * 60 * 1000)
    public void refreshValue(){
        String key = "current_percentage";

        Mono<Double> newValue = fetchNewValue(key);

        PercentageEntity entity = new PercentageEntity();
        entity.setId(key);
        entity.setValue(Double.parseDouble(newValue.block().toString()));
        entity.setUpdatedAt(java.time.LocalDateTime.now());

        jpaRepository.save(entity);
    }

    public Mono<Double> fetchNewValue(String key) {
        return webClient.get()
                .uri(THIRD_PARTY_URL)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> {
                    if (response == null || response.isEmpty()) {
                        logger.severe("Received empty response from third party; using db");
                        return Mono.error(new PercentageException("Empty response from percentage provider"));
                    }
                    try {
                        Double percentage = Double.parseDouble(response.trim());
                        return Mono.just(percentage*100);
                    } catch (NumberFormatException e) {
                        logger.severe("Error parsing percentage from response: " + e.getMessage());
                        return Mono.error(new PercentageException(response));
                    }
                }).onErrorResume(e -> {
                    logger.severe("Error calling third party: using db" + e.getMessage());
                    return Mono.fromCallable(() -> this.getValue(key)).subscribeOn(Schedulers.boundedElastic());
                });
    }
}
