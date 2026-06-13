package com.techfinance.pessoal.api.auth.adapter.in.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.techfinance.pessoal.api.infra.shared.validation.annotations.RequiredField;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RegisterRequest", description = "DTO para requisição de registro de usuário")
@JsonIgnoreProperties(ignoreUnknown = true)
public record RegisterRequest(

    @Schema(description = "Nome completo do usuário", example = "João Vitor")
    @JsonProperty(value = "nome")
    @RequiredField(property = "nome")
    String name,

    @Schema(description = "Nome de usuário do account", example = "joao.vitor")
    @JsonProperty(value = "usuario")
    @RequiredField(property = "usuario")
    String username,

    @Schema(description = "Senha do account", example = "senha123")
    @JsonProperty(value = "senha")
    @RequiredField(property = "senha")
    String password,

    @Schema(
        description = "Perfil do usuário",
        example = "USER",
        allowableValues = {"USER", "ADMIN"}
    )
    @JsonProperty(value = "papel")
    @RequiredField(property = "papel")
    String role
) {}
