package com.tenpo.challenge.domain.repository;

import java.util.List;

import com.tenpo.challenge.domain.model.CallHistory;

public interface CallHistoryRepository {
    CallHistory save(CallHistory history);
    List<CallHistory> findAll();
}
    