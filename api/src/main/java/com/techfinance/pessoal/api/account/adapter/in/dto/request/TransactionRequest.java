package com.techfinance.pessoal.api.account.adapter.in.dto.request;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.techfinance.pessoal.api.account.domain.enums.TransactionType;

public record TransactionRequest(
    UUID accountId,
    UUID categoryId,
    BigDecimal amount,
    TransactionType type,
    String description,
    Instant occurredAt
) {}