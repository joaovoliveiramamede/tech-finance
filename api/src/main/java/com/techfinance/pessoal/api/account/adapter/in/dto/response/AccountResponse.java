package com.techfinance.pessoal.api.account.adapter.in.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record AccountResponse(
    UUID id,
    BigDecimal balance,
    Instant createdAt,
    Instant updatedAt
) {}