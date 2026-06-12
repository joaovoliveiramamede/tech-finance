package com.techfinance.pessoal.api.account.adapter.in.dto.request;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.techfinance.pessoal.api.account.domain.enums.TransactionType;
import com.techfinance.pessoal.api.infra.shared.validation.annotations.RequiredField;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TransactionRequest(
    
    @JsonProperty(value = "id_conta")
    @RequiredField(property = "id_conta")
    UUID accountId,

    @JsonProperty(value = "id_categoria")
    @RequiredField(property = "id_categoria")
    UUID categoryId,

    @JsonProperty(value = "valor")
    @RequiredField(property = "valor")
    BigDecimal amount,
    
    @JsonProperty(value = "tipo")
    @RequiredField(property = "tipo")
    TransactionType type,

    @JsonProperty(value = "descricao")
    @RequiredField(property = "descricao")
    String description,

    @JsonFormat(
        shape = Shape.STRING,
        pattern = "dd-MM-yyyy : HH:mm",
        timezone = "America/Sao_Paulo"
    )
    @JsonProperty(value = "ocorreu_em")
    @RequiredField(property = "ocorreu_em")
    Instant occurredAt
) {}