package com.techfinance.pessoal.api.auth.adapter.in.dto.response;

import java.util.UUID;

public record AuthResponse(
        UUID userId,
        String name,
        String email,
        String token
) {
}