package com.techfinance.pessoal.desktop.dto.response;

import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CategoryResponse(

    @JsonProperty("id")
    UUID id,

    @JsonProperty("nome")
    String name,

    @JsonProperty("descricao")
    String description,

    @JsonProperty("data_criacao")
    Instant createdAt,

    @JsonProperty("data_atualizacao")
    Instant updatedAt
) {}
