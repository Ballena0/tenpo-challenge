package com.tenpo.challenge.domain.repository;

import com.tenpo.challenge.domain.model.Percentage;
import com.tenpo.challenge.interfaces.exception.PercentageException;

import reactor.core.publisher.Mono;

public interface PercentageRepository {
    Mono<Double> getPercentage() throws PercentageException;
    Percentage save(Percentage percentage);
    Percentage findTopByOrderByIdDesc();
}
