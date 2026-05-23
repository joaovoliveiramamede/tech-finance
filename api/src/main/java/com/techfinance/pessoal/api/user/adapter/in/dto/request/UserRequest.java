package com.techfinance.pessoal.api.user.adapter.in.dto.request;

import com.techfinance.pessoal.api.user.domain.enums.Role;

public record UserRequest (
    String name,
    String username,
    String password,
    Role role
){}