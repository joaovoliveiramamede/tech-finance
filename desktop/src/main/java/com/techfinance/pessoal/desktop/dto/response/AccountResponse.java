package com.techfinance.pessoal.desktop.dto.response;

import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.techfinance.pessoal.desktop.dto.enums.AccountType;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AccountResponse(

    @JsonProperty("id")
    UUID id,

    @JsonProperty("nome")
    String name,

    @JsonProperty("saldo")
    java.math.BigDecimal balance,

    @JsonProperty("tipo")
    AccountType type,

    @JsonProperty("id_usuario")
    UUID userId,

    @JsonProperty("data_criacao")
    Instant createdAt,

    @JsonProperty("data_atualizacao")
    Instant updatedAt
) {}
