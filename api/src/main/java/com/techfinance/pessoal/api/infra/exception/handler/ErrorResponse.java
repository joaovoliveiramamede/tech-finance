package com.techfinance.pessoal.api.infra.exception.handler;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorResponse(

    @JsonProperty("mensagem")
    String message,

    @JsonProperty("data")
    Instant timestamp
) {
    public static ErrorResponse of(String message) {
        return new ErrorResponse(message, Instant.now());
    }
}
