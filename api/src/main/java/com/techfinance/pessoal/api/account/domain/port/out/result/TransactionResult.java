package com.techfinance.pessoal.api.account.domain.port.out.result;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.techfinance.pessoal.api.account.domain.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResult {
    private UUID id;
    private BigDecimal amount;
    private TransactionType type;
    private String description;
    private Instant occurredAt;
    private AccountResult account;
    private CategoryResult category;
    private Instant createdAt;
    private Instant updatedAt;
}