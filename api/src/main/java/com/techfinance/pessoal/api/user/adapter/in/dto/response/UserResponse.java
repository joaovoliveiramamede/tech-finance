package com.techfinance.pessoal.api.user.adapter.in.dto.response;

import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.techfinance.pessoal.api.user.domain.enums.Role;
import com.techfinance.pessoal.api.user.domain.port.out.result.UserResult;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
public record UserResponse(

    @JsonProperty(value = "id")
    UUID id,

    @JsonProperty(value = "nome")
    String name,

    @JsonProperty(value = "usuario")
    String username,

    @JsonProperty(value = "papel")
    Role role,

    @JsonProperty(value = "data_criacao")
    Instant createdAt,

    @JsonProperty(value = "data_atualizacao")
    Instant updatedAt
) {
    public static UserResponse from(UserResult result) {
        return new UserResponse(
            result.getId(),
            result.getName(),
            result.getUsername(),
            result.getRole(),
            result.getCreatedAt(),
            result.getUpdatedAt()
        );
    }
}