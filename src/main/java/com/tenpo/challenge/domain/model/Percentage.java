package com.tenpo.challenge.domain.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Percentage {
    String id;
    Double value;
    LocalDateTime updatedAt;
}
