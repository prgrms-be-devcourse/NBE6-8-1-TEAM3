package com.backend.api.v1.orders.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private String message;
    private int code;

    public ErrorResponse(String message, int code) {
        this.message = message;
        this.code = code;
    }
}
