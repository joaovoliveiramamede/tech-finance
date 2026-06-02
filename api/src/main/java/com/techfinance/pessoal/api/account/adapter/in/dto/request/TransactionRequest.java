package com.techfinance.pessoal.api.account.adapter.in.dto.request;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.techfinance.pessoal.api.account.domain.enums.TransactionType;

public record TransactionRequest(
    
    @JsonProperty(value = "id_conta")
    UUID accountId,

    @JsonProperty(value = "id_categiria")
    UUID categoryId,

    @JsonProperty(value = "valor")
    BigDecimal amount,
    
    @JsonProperty(value = "tipo")
    TransactionType type,

    @JsonProperty(value = "descricao")
    String description,

    @JsonFormat(
        shape = Shape.STRING,
        pattern = "dd-MM-yyyy : HH:mm",
        timezone = "America/Sao_Paulo"
    )
    @JsonProperty(value = "ocorreu_em")
    Instant occurredAt
) {}