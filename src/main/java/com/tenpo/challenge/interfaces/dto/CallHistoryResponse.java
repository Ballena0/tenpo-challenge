package com.tenpo.challenge.interfaces.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CallHistoryResponse {
    private Long id;
    private Double operand1;
    private Double operand2;
    private String endpoint;
    private LocalDateTime timestamp;
    private Double statusCode;
    private String response;
}
