package com.tenpo.challenge.domain.repository;

import com.tenpo.challenge.infrastructure.persistence.CallHistoryJpaRepository;
import com.tenpo.challenge.infrastructure.persistence.CallHistoryRepositoryAdapter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.mockito.Mock;

@ExtendWith(MockitoExtension.class)
public class CallHistoryRepositoryAdapterTest {
    
    @Mock
    private CallHistoryJpaRepository callHistoryJpaRepository;

    private CallHistoryRepositoryAdapter callHistoryRepositoryAdapter;

    @Test
    void saveTest() {
        // implement
        assertTrue(true);
    }
}
