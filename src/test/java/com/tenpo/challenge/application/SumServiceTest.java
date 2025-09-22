package com.tenpo.challenge.application;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.tenpo.challenge.domain.model.CallHistory;
import com.tenpo.challenge.domain.repository.CallHistoryRepository;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class SumServiceTest {

    @TestConfiguration
    static class TestSchedulerConfig {
        @Bean
        Scheduler testSchedulerConfig() {
            return Schedulers.immediate();
        }
    }

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
        when(percentageService.fetchNewValue("current_percentage")).thenReturn(Mono.just(25.0));
        StepVerifier.create(percentageService.fetchNewValue("current_percentage")).expectNext(25.0).verifyComplete();

        Double a = 5.0;
        Double b = 3.0;
        Double expected = 10.0;

        Mono<Double> result = sumService.sum(a, b);

        verify(percentageService, times(0)).getValue("current_percentage");
        verify(percentageService, times(2)).fetchNewValue("current_percentage");
        StepVerifier.create(result)
            .expectNext(expected)
            .verifyComplete();
    }

    @Test
    void testSumWithNegativeNumbers() {
        when(percentageService.fetchNewValue("current_percentage")).thenReturn(Mono.just(25.0));
        Double a = -2.0;
        Double b = -3.0;
        Double expected = -6.25;
        Mono<Double> result = sumService.sum(a, b);

        StepVerifier.create(result)
            .expectNext(expected)
            .verifyComplete();

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
