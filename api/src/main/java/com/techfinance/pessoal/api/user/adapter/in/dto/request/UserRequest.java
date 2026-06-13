package com.techfinance.pessoal.api.user.adapter.in.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.techfinance.pessoal.api.infra.shared.validation.annotations.RequiredField;
import com.techfinance.pessoal.api.user.domain.enums.Role;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserRequest (

    @JsonProperty(value = "nome")
    @RequiredField(property = "nome")
    String name,

    @JsonProperty(value = "usuario")
    @RequiredField(property = "usuario")
    String username,

    @JsonProperty(value = "senha")
    @RequiredField(property = "senha")
    String password,
    
    @JsonProperty(value = "papel")
    @RequiredField(property = "papel")
    Role role
){}