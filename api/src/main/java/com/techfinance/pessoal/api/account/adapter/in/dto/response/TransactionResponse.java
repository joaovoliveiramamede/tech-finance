package com.techfinance.pessoal.api.account.adapter.in.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.techfinance.pessoal.api.account.domain.enums.TransactionType;
import com.techfinance.pessoal.api.account.domain.port.out.result.TransactionResult;

public record TransactionResponse(

    UUID id,

    BigDecimal amount,

    TransactionType type,

    String description,

    Instant occurredAt,

    Instant createdAt,

    Instant updatedAt

) {
    public static TransactionResponse from(TransactionResult result) {
        return new TransactionResponse(
            result.getId(),
            result.getAmount(),
            result.getType(),
            result.getDescription(),
            result.getOccurredAt(),
            result.getCreatedAt(),
            result.getUpdatedAt()
        );
    }
}