package com.tenpo.challenge.interfaces.dto;

import lombok.Data;

@Data
public class SumResponse {
    private Double result;

    public SumResponse(Double result) {
        this.result = result;
    }
}
