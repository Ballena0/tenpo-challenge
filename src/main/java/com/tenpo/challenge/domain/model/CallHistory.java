package com.tenpo.challenge.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "call_history")
public class CallHistory {
    @Id
    private Long id;
    
    private String endpoint;
    private String requestPayload;
}
