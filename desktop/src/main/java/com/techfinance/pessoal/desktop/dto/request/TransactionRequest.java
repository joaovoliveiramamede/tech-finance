package com.techfinance.pessoal.desktop.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.techfinance.pessoal.desktop.dto.enums.TransactionType;

public record TransactionRequest(

    @JsonProperty("id_conta")
    UUID accountId,

    @JsonProperty("id_categoria")
    UUID categoryId,

    @JsonProperty("valor")
    BigDecimal amount,

    @JsonProperty("tipo")
    TransactionType type,

    @JsonProperty("descricao")
    String description,

    @JsonProperty("ocorreu_em")
    String occurredAt
) {}
