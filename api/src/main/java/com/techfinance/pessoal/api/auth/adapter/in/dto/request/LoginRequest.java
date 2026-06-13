package com.techfinance.pessoal.api.auth.adapter.in.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.techfinance.pessoal.api.infra.shared.validation.annotations.RequiredField;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LoginRequest(

    @JsonProperty(value = "usuario")
    @RequiredField(property = "usuario")
    String username,

    @JsonProperty(value = "senha")
    @RequiredField(property = "senha")
    String password
) {}
