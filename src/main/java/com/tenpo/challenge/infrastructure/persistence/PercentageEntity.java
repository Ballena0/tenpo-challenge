package com.tenpo.challenge.infrastructure.persistence;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class PercentageEntity {
    @Id
    private String id;
    private Double value;
    private LocalDateTime updatedAt;
}
