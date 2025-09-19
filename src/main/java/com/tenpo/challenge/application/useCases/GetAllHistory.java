package com.tenpo.challenge.application.useCases;

import java.util.List;

import com.tenpo.challenge.domain.model.CallHistory;

public interface GetAllHistory {
    List<CallHistory> getAllhistory();
}
