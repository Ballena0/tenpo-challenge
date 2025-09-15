package com.tenpo.challenge.domain.repository;

import com.tenpo.challenge.domain.model.CallHistory;
import com.tenpo.challenge.infrastructure.persistence.CallHistoryEntity;
import com.tenpo.challenge.infrastructure.persistence.CallHistoryJpaRepository;
import com.tenpo.challenge.infrastructure.persistence.CallHistoryRepositoryAdapter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CallHistoryRepositoryAdapterTest {
    
    @Mock
    private CallHistoryJpaRepository callHistoryJpaRepository;

    private CallHistoryRepositoryAdapter callHistoryRepositoryAdapter;

    @BeforeEach
    void setUp() {
        callHistoryRepositoryAdapter = new CallHistoryRepositoryAdapter(callHistoryJpaRepository);
    }

    @Test
    void saveTest() {
        // AAA
        // A
        CallHistory callHistory = new CallHistory(
            null, 5.0, 10.0, "/api/sum",
            java.time.LocalDateTime.now(), 200L, "{\"result\":15.0}"
        );

        CallHistoryEntity entity = new CallHistoryEntity();
        entity.setId(1L);
        entity.setOperand1(callHistory.getOperand1());
        entity.setOperand2(callHistory.getOperand2());
        entity.setEndpoint(callHistory.getEndpoint());
        entity.setTimestamp(callHistory.getTimestamp());
        entity.setStatusCode(callHistory.getStatusCode());
        entity.setResponse(callHistory.getResponse());

        when(callHistoryJpaRepository.save(any(CallHistoryEntity.class))).thenReturn(entity);

        // A
        CallHistory result = callHistoryRepositoryAdapter.save(callHistory);

        // assert
        assertNotNull(result);
    }
}
