package com.techfinance.pessoal.api.account.adapter.in.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.techfinance.pessoal.api.account.domain.enums.AccountType;
import com.techfinance.pessoal.api.infra.shared.validation.annotations.RequiredField;

import java.math.BigDecimal;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AccountRequest(

        @JsonProperty(value = "nome")
        @RequiredField(property = "nome")
        String name,

        @JsonProperty(value = "valor")
        @RequiredField(property = "valor")
        BigDecimal balance,

        @JsonProperty(value = "tipo")
        @RequiredField(property = "tipo")
        AccountType type,

        @JsonProperty(value = "id_usuario")
        @RequiredField(property = "id_usuario")
        UUID userId
) {}