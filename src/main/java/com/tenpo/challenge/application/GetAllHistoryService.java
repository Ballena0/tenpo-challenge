package com.tenpo.challenge.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tenpo.challenge.application.useCases.GetAllHistory;
import com.tenpo.challenge.domain.model.CallHistory;
import com.tenpo.challenge.domain.repository.CallHistoryRepository;

@Service
public class GetAllHistoryService implements GetAllHistory {
    private final CallHistoryRepository callHistoryRepository;

    public GetAllHistoryService(CallHistoryRepository callHistoryRepository) {
        this.callHistoryRepository = callHistoryRepository;
    }

    @Override
    public List<CallHistory> getAllhistory() {
        return callHistoryRepository.findAll();
    }
}
