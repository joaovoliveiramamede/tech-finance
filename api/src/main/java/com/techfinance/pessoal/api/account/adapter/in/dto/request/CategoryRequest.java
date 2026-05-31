package com.techfinance.pessoal.api.account.adapter.in.dto.request;

import java.math.BigDecimal;

import com.techfinance.pessoal.api.account.domain.enums.TransactionType;

public record CategoryRequest(
    BigDecimal amount,
    TransactionType type,
    String description
) {}