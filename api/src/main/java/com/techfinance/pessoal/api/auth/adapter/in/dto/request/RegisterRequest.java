package com.techfinance.pessoal.api.auth.adapter.in.dto.request;

public record RegisterRequest(
        String name,
        String email,
        String password
) {
}
