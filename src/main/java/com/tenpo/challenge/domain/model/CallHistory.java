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
    private double operand1;
    private double operand2;
    private String endpoint;
    private LocalDateTime timestamp;
    private Long statusCode;
    private String response;
}
