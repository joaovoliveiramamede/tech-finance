package com.techfinance.pessoal.desktop.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginRequest(

    @JsonProperty("usuario")
    String username,

    @JsonProperty("senha")
    String password
) {}
