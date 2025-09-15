package com.tenpo.challenge.infrastructure.persistence;

import org.springframework.stereotype.Component;
import com.tenpo.challenge.domain.model.CallHistory;
import com.tenpo.challenge.domain.repository.CallHistoryRepository;

@Component
public class CallHistoryRepositoryAdapter implements CallHistoryRepository {
    
    private final CallHistoryJpaRepository jpaRepository;

    public CallHistoryRepositoryAdapter(CallHistoryJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public CallHistory save(CallHistory callHistory) {
        CallHistoryEntity entity = new CallHistoryEntity();
        entity.setOperand1(callHistory.getOperand1());
        entity.setOperand2(callHistory.getOperand2());
        entity.setEndpoint(callHistory.getEndpoint());
        entity.setTimestamp(callHistory.getTimestamp());
        entity.setStatusCode(callHistory.getStatusCode());
        entity.setResponse(callHistory.getResponse());

        CallHistoryEntity savedEntity = jpaRepository.save(entity);

        callHistory.setId(savedEntity.getId());
        return callHistory;
    }
}
