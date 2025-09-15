package com.tenpo.challenge.interfaces.dto;

import lombok.Data;

@Data
public class SumResponse {
    private double result;

    public SumResponse(double result) {
        this.result = result;
    }
}
