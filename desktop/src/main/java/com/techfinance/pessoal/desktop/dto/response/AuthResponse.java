package com.techfinance.pessoal.desktop.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthResponse(

    @JsonProperty("nome")
    String name,

    @JsonProperty("usuario")
    String username,

    @JsonProperty("papel")
    String role,

    @JsonProperty("token")
    String token
) {}
