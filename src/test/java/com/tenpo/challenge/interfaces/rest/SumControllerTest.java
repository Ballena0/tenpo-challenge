package com.tenpo.challenge.interfaces.rest;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.List;

import com.tenpo.challenge.application.GetAllHistoryService;
import com.tenpo.challenge.application.SumService;
import com.tenpo.challenge.domain.model.CallHistory;
import com.tenpo.challenge.domain.repository.TimeProvider;
import com.tenpo.challenge.interfaces.dto.SumRequest;

import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class SumControllerTest {
    
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private SumService sumService;

    @MockBean
    private GetAllHistoryService getAllHistory;

    @MockBean
    private TimeProvider timeProvider;

    @Test
    void sumWithNullRequestShouldReturnBadRequest() throws Exception{
        LocalDateTime dateTime = LocalDateTime.of(2025, 9, 15, 12, 0);
        when(timeProvider.now()).thenReturn(dateTime);

        webTestClient.post().uri("/api/sum")
        .exchange()
        .expectStatus().isBadRequest();

        verify(sumService, never()).sum(anyDouble(), anyDouble());
        verify(sumService, times(1))
        .saveErrorCallHistoryAsync(
            isNull(),
            isNull(),
            anyDouble(),
            eq("/api/sum"),
            eq(dateTime),
            eq("null request body")
        );
    }

    @Test
    void sumWithNullOperandShouldReturnBadRequest() throws Exception{
        // AAA
        // arrange
        LocalDateTime dateTime = LocalDateTime.of(2025, 9, 15, 12, 0);
        when(timeProvider.now()).thenReturn(dateTime);

        SumRequest request = new SumRequest();
        request.setOperand1(5D);
        request.setOperand2(null);

        // act
        webTestClient.post().uri("/api/sum")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue("{\"operand1\":5,\"operand2\":null}")
        .exchange()
        .expectStatus().isBadRequest();

        // assert
        verify(sumService, never()).sum(anyDouble(), anyDouble());
        verify(sumService, times(1))
        .saveErrorCallHistoryAsync(
            eq(5D),
            isNull(),
            anyDouble(),
            eq("/api/sum"),
            eq(dateTime),
            eq("operands cannot be null")
        );
    }

    @Test
    void sumWithValidOperandsShouldReturnOk() throws Exception{
        // AAA
        // arrange
        LocalDateTime dateTime = LocalDateTime.of(2025, 9, 15, 12, 0);
        when(timeProvider.now()).thenReturn(dateTime);
        when(sumService.sum(5D, 3D)).thenReturn(Mono.just(8D));

        // A
        webTestClient.post().uri("/api/sum")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue("{\"operand1\":5,\"operand2\":3}")
        .exchange()
        .expectStatus().isOk()
        .expectBody().json("{\"result\":8}");

        // assert
        verify(sumService, times(1)).sum(5D, 3D);
        verify(sumService, times(1))
        .saveSuccessCallHistoryAsync(
            eq(5D),
            eq(3D),
            anyDouble(),
            eq("/api/sum"),
            eq(dateTime),
            eq("8.0")
        );
    }

    @Test
    void getAllHistoryShouldReturnOk() throws Exception{
        CallHistory record1 = new CallHistory(1L, 5D, 3D, "/api/sum", LocalDateTime.of(2025, 9, 15, 12, 0), 200D, null);
        CallHistory record2 = new CallHistory(2L, null, 3D, "/api/sum", LocalDateTime.of(2025, 9, 15, 12, 5), null, "operands cannot be null");
        CallHistory record3 = new CallHistory(3L, 5D, null, "/api/sum", LocalDateTime.of(2025, 9, 15, 12, 0), 400D, "operands cannot be null");
        

        List<CallHistory> records = List.of(record1, record2, record3);
        when(getAllHistory.getAllhistory()).thenReturn(records);

        webTestClient.get().uri("/api/history")
        .exchange()
        .expectStatus().isOk()
        .expectBody().jsonPath("$[0].id").isEqualTo(1)
        .jsonPath("$[0].operand1").isEqualTo(5)
        .jsonPath("$[0].operand2").isEqualTo(3)
        .jsonPath("$[0].endpoint").isEqualTo("/api/sum");

        verify(getAllHistory, times(1)).getAllhistory();
    }
}