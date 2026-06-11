package com.techfinance.pessoal.api.account.domain.port.out.result;

import java.time.Instant;
import java.util.UUID;

public record CategoryResult(
    UUID id,
    String name,
    String description,
    Instant createdAt,
    Instant updatedAt
) {}
