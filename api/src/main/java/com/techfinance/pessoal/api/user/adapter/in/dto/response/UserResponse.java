package com.techfinance.pessoal.api.user.adapter.in.dto.response;

import java.time.Instant;
import java.util.UUID;

public record UserResponse(
    UUID id,
    String username,
    String password,
    Instant createdAt,
    Instant updatedAt
) {}