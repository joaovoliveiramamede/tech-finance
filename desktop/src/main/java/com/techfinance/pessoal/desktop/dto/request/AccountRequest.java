package com.techfinance.pessoal.desktop.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.techfinance.pessoal.desktop.dto.enums.AccountType;

public record AccountRequest(

    @JsonProperty("nome")
    String name,

    @JsonProperty("valor")
    BigDecimal balance,

    @JsonProperty("tipo")
    AccountType type,

    @JsonProperty("id_usuario")
    UUID userId
) {}
