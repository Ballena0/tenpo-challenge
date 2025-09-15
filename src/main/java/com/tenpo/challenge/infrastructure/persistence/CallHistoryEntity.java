package com.tenpo.challenge.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class CallHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double operand1;
    private double operand2;
    private String endpoint;
    private LocalDateTime timestamp;
    private Long statusCode;
    private String response;
}
