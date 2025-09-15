package com.tenpo.challenge.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallHistory {
    
    private Long id;
    private Double operand1;
    private Double operand2;
    private String endpoint;
    private LocalDateTime timestamp;
    private Double statusCode;
    private String response;
}
