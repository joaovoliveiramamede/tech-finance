package com.techfinance.pessoal.desktop.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RegisterRequest(

    @JsonProperty("nome")
    String name,

    @JsonProperty("usuario")
    String username,

    @JsonProperty("senha")
    String password,

    @JsonProperty("papel")
    String role
) {}
