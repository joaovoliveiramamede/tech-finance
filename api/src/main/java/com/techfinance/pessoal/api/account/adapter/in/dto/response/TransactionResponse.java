package com.techfinance.pessoal.api.account.adapter.in.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.techfinance.pessoal.api.account.domain.enums.TransactionType;

public record TransactionResponse(

    UUID id,

    UUID accountId,
    String accountName,

    UUID categoryId,
    String categoryName,

    BigDecimal amount,

    TransactionType type,

    String description,

    Instant occurredAt,

    Instant createdAt,

    Instant updatedAt

) {}