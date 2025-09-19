package com.tenpo.challenge.interfaces.rest;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import com.tenpo.challenge.application.GetAllHistoryService;
import com.tenpo.challenge.application.SumService;
import com.tenpo.challenge.domain.model.CallHistory;
import com.tenpo.challenge.domain.repository.TimeProvider;
import com.tenpo.challenge.interfaces.dto.SumRequest;

@SpringBootTest
@AutoConfigureMockMvc
public class SumControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

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

        mockMvc.perform(post("/api/sum")
        .contentType("application/json"))
        .andExpect(status().isBadRequest());

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
        mockMvc.perform(post("/api/sum")
        .contentType("application/json")
        .content("{\"operand1\":5,\"operand2\":null}"))
        .andExpect(status().isBadRequest());

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
        when(sumService.sum(5D, 3D)).thenReturn(8D);

        // A
        mockMvc.perform(post("/api/sum")
        .contentType("application/json")
        .content("{\"operand1\":5,\"operand2\":3}"))
        .andExpect(status().isOk())
        .andExpect(content().json("{\"result\":8}"));

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

        mockMvc.perform(get("/api/history")
        .contentType("application/json"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].operand1").value(5))
        .andExpect(jsonPath("$[0].operand2").value(3))
        .andExpect(jsonPath("$[0].endpoint").value("/api/sum"));

        verify(getAllHistory, times(1)).getAllhistory();
    }
}