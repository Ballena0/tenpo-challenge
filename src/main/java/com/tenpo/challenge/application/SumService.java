package com.tenpo.challenge.application;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.tenpo.challenge.domain.model.CallHistory;
import com.tenpo.challenge.domain.repository.CallHistoryRepository;

@Service
public class SumService {
    private final CallHistoryRepository callHistoryRepository;

    public SumService(CallHistoryRepository callHistoryRepository) {
        this.callHistoryRepository = callHistoryRepository;
    }

    public double sum(double a, double b){
        double result = a + b;
        return result;
    }

    public void saveCallHistory(double a, double b, double statusCode, String endpoint, LocalDateTime date, String response){ 
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
        } catch (Exception e) {
            System.out.println("Error saving call history: " + e.getMessage());
        };
    }
}
