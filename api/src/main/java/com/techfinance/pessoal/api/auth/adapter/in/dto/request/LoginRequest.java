package com.techfinance.pessoal.api.auth.adapter.in.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techfinance.pessoal.api.infra.shared.validation.annotations.RequiredField;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LoginRequest(

    @RequiredField(property = "username")
    String username,

    @RequiredField(property = "password")
    String password
) {}
