package com.tenpo.challenge.infrastructure.persistence;

import jakarta.persistence.Column;
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
    @Column(nullable = true)
    private Double operand1;
    @Column(nullable = true)
    private Double operand2;
    private String endpoint;
    private LocalDateTime timestamp;
    private Double statusCode;
    private String response;
}
