package com.tenpo.challenge.application;

import org.springframework.stereotype.Service;

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
}
