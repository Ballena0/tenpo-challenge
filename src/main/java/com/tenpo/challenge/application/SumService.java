package com.tenpo.challenge.application;

import java.time.LocalDateTime;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.tenpo.challenge.domain.model.CallHistory;
import com.tenpo.challenge.domain.repository.CallHistoryRepository;

import reactor.core.publisher.Mono;

@Service
public class SumService {
    private static final Logger logger = LoggerFactory.getLogger(SumService.class);
    private final CallHistoryRepository callHistoryRepository;
    private final PercentageService percentageService;

    public SumService(CallHistoryRepository callHistoryRepository, PercentageService percentageService) {
        this.callHistoryRepository = callHistoryRepository;
        this.percentageService = percentageService;
    }

    public Mono<Double> sum(Double a, Double b) {
    return percentageService.fetchNewValue("current_percentage")
            .map(percentage -> {
                Double total = (a + b) * (1 + (percentage / 100));
                return total;
            });
    }

    @Async
    public void saveSuccessCallHistoryAsync(Double a, Double b, Double statusCode, String endpoint, LocalDateTime date, String response) {
        saveCallHistoryAsync(a, b, statusCode, endpoint, date, response);
    }

    @Async
    public void saveErrorCallHistoryAsync(Double a, Double b, Double statusCode, String endpoint, LocalDateTime date, String response) {
        saveCallHistoryAsync(a, b, statusCode, endpoint, date, response);
    }

    public void saveCallHistoryAsync(Double a, Double b, Double statusCode, String endpoint, LocalDateTime date, String response){ 
        try {
            CallHistory callHistory = new CallHistory(
            null,
            a,
            b,
            endpoint,
            date,
            statusCode,
            response
        );

            callHistoryRepository.save(callHistory);
            logger.info("Call history saved!!");
        } catch (Exception e) {
            logger.error("Error saving call history: " + e.getMessage());
        };
    }
}
