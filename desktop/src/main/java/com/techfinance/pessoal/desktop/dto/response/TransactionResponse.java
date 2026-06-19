package com.techfinance.pessoal.desktop.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.techfinance.pessoal.desktop.dto.enums.TransactionType;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TransactionResponse(

    @JsonProperty("id")
    UUID id,

    @JsonProperty("valor")
    BigDecimal amount,

    @JsonProperty("tipo")
    TransactionType type,

    @JsonProperty("descricao")
    String description,

    @JsonProperty("ocorreu_em")
    Instant occurredAt,

    @JsonProperty("id_conta")
    UUID accountId,

    @JsonProperty("nome_conta")
    String accountName,

    @JsonProperty("id_categoria")
    UUID categoryId,

    @JsonProperty("nome_categoria")
    String categoryName,

    @JsonProperty("data_criacao")
    Instant createdAt,

    @JsonProperty("data_atualizacao")
    Instant updatedAt
) {}
