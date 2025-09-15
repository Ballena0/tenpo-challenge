package com.tenpo.challenge.domain.repository;

import com.tenpo.challenge.domain.model.CallHistory;

public interface CallHistoryRepository {
    CallHistory save(CallHistory history);
}
    