package com.techfinance.pessoal.api.auth.adapter.in.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.techfinance.pessoal.api.auth.domain.port.out.result.AuthResult;

import io.swagger.v3.oas.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Schema(name = "AuthResponse", description = "DTO para resposta de autenticação")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
public record AuthResponse(

    @Schema(description = "Nome completo do usuário", example = "João Vitor")
    @JsonProperty(value = "nome")
    String name,

    @Schema(description = "Nome de usuário do account", example = "joao.vitor")
    @JsonProperty(value = "usuario")
    String username,

    @Schema(
        description = "Perfil do usuário",
        example = "USER",
        allowableValues = {"USER", "ADMIN"}
    )
    @JsonProperty(value = "papel")
    String role,

    @Schema(description = "Token JWT de autenticação", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    @JsonProperty(value = "token")
    String token
) {
    public static AuthResponse from(AuthResult result) {
        return new AuthResponse(
            result.getName(),
            result.getUsername(),
            result.getRole(),
            result.getToken()
        );
    }
}
