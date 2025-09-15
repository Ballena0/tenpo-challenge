package com.tenpo.challenge.interfaces.dto;

import lombok.Data;

@Data
public class SumErrorResponse {
    private Double statusCode;
    private String message;
}
