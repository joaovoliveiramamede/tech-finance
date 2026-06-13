package com.techfinance.pessoal.desktop.dto.response;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ErrorResponse(

    @JsonProperty("mensagem")
    String message,

    @JsonProperty("data")
    Instant timestamp
) {}
