package com.techfinance.pessoal.api.auth.adapter.in.dto.request;

public record LoginRequest(
        String username,
        String password
) {
}
