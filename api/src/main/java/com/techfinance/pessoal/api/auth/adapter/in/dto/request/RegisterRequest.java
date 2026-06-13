package com.techfinance.pessoal.api.auth.adapter.in.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techfinance.pessoal.api.infra.shared.validation.annotations.RequiredField;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RegisterRequest(

    @RequiredField(property = "name")
    String name,

    @RequiredField(property = "username")
    String username,

    @RequiredField(property = "password")
    String password,

    @RequiredField(property = "role")
    String role
) {}
