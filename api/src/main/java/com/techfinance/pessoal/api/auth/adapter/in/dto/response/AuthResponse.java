package com.techfinance.pessoal.api.auth.adapter.in.dto.response;

public record AuthResponse(
        String username,
        String token
) {}