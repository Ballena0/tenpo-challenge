package com.tenpo.challenge.application;

import java.time.LocalDateTime;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.tenpo.challenge.domain.model.CallHistory;
import com.tenpo.challenge.domain.repository.CallHistoryRepository;

@Service
public class SumService {
    private static final Logger logger = LoggerFactory.getLogger(SumService.class);
    private final CallHistoryRepository callHistoryRepository;

    public SumService(CallHistoryRepository callHistoryRepository) {
        this.callHistoryRepository = callHistoryRepository;
    }

    public double sum(double a, double b){
        double result = a + b;
        return result;
    }

    @Async
    public void saveCallHistoryAsync(Double a, Double b, double statusCode, String endpoint, LocalDateTime date, String response){ 
        try {
            CallHistory callHistory = new CallHistory(
            null,
            a,
            b,
            endpoint,
            date,
            (long) statusCode,
            response
        );

            callHistoryRepository.save(callHistory);
            logger.info("Call history saved!!");
        } catch (Exception e) {
            logger.error("Error saving call history: " + e.getMessage());
        };
    }
}
