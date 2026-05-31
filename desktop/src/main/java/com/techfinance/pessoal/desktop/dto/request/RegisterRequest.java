package com.techfinance.pessoal.desktop.dto.request;

public record RegisterRequest(
        String name,
        String username,
        String password,
        String role
) {}