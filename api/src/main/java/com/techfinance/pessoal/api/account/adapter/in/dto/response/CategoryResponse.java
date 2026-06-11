package com.techfinance.pessoal.api.account.adapter.in.dto.response;

import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.techfinance.pessoal.api.account.domain.port.out.result.CategoryResult;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
public record CategoryResponse(

    @JsonProperty(value = "id")
    UUID id,

    @JsonProperty(value = "nome")
    String name,

    @JsonProperty(value = "descricao")
    String description,

    @JsonProperty(value = "data_criacao")
    Instant createdAt,

    @JsonProperty(value = "data_atualizacao")
    Instant updatedAt
) {

    public static CategoryResponse from(CategoryResult result) {
        return new CategoryResponse(
            result.getId(),
            result.getName(),
            result.getDescription(),
            result.getCreatedAt(),
            result.getUpdatedAt()
        );
    }
}
