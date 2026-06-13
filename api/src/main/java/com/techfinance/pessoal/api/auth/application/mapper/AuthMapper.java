package com.techfinance.pessoal.api.auth.application.mapper;

import org.springframework.stereotype.Component;

import com.techfinance.pessoal.api.auth.adapter.in.dto.request.RegisterRequest;
import com.techfinance.pessoal.api.user.adapter.in.dto.request.UserRequest;
import com.techfinance.pessoal.api.user.domain.enums.Role;

@Component
public class AuthMapper  {

    public UserRequest toUserRequest(RegisterRequest request) {
        return new UserRequest(
            request.name(),
            request.username(),
            request.password(),
            Role.valueOf(request.role())
        );
    }
}
