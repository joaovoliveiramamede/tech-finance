package com.techfinance.pessoal.api.auth.adapter.in.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.techfinance.pessoal.api.auth.domain.port.out.result.AuthResult;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
public record AuthResponse(

    @JsonProperty(value = "nome")
    String name,

    @JsonProperty(value = "usuario")
    String username,

    @JsonProperty(value = "papel")
    String role,

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
