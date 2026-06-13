package com.techfinance.pessoal.api.auth.adapter.in.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.techfinance.pessoal.api.infra.shared.validation.annotations.RequiredField;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LoginRequest", description = "DTO para requisição de login")
@JsonIgnoreProperties(ignoreUnknown = true)
public record LoginRequest(

    @Schema(description = "Nome de usuário do account", example = "joao.vitor")
    @JsonProperty(value = "usuario")
    @RequiredField(property = "usuario")
    String username,

    @Schema(description = "Senha do account", example = "senha123")
    @JsonProperty(value = "senha")
    @RequiredField(property = "senha")
    String password
) {}
