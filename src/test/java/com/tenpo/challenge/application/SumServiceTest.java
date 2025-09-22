package com.tenpo.challenge.application;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.tenpo.challenge.domain.model.CallHistory;
import com.tenpo.challenge.domain.repository.CallHistoryRepository;
import com.tenpo.challenge.infrastructure.persistence.PercentageJpaRepository;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class SumServiceTest {

    @Mock
    private CallHistoryRepository callHistoryRepository;

    @Mock
    private PercentageService percentageService;

    private SumService sumService;

    @BeforeEach
    void setUp() {
        sumService = new SumService(callHistoryRepository, percentageService);
    }

    @Test
    void testSumCorrectResult() {
        Double a = 5.0;
        Double b = 3.0;
        Double expected = 8.0;

        Mono<Double> result = sumService.sum(a, b);

        assert result.equals(expected);
    }

    @Test
    void testSumWithNegativeNumbers() {
        Double a = -2.0;
        Double b = -3.0;
        Double expected = -5.0;

        Mono<Double> result = sumService.sum(a, b);

        assert result.equals(expected);
    }

    @Test
    void testSaveCallHistoryASync() {
        Double a = 5.0;
        Double b = 3.0;
        Double result = 8.0;

        sumService.saveCallHistoryAsync(a, b, 200.0, "/api/sum", null, result.toString());

        ArgumentCaptor<CallHistory> captor = ArgumentCaptor.forClass(CallHistory.class);
        verify(callHistoryRepository, times(1)).save(captor.capture());

        CallHistory savedCallHistory = captor.getValue();
        assert savedCallHistory.getOperand1().equals(a);
        assert savedCallHistory.getOperand2().equals(b);
        assert savedCallHistory.getEndpoint().equals("/api/sum");
    }
}
