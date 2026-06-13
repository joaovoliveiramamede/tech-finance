package com.techfinance.pessoal.api.auth.application.mapper;

import org.springframework.stereotype.Component;

import com.techfinance.pessoal.api.auth.adapter.in.dto.request.RegisterRequest;
import com.techfinance.pessoal.api.auth.adapter.in.dto.response.AuthResponse;
import com.techfinance.pessoal.api.auth.domain.port.out.result.AuthResult;
import com.techfinance.pessoal.api.infra.shared.converter.MapperConverter;
import com.techfinance.pessoal.api.user.adapter.in.dto.request.UserRequest;
import com.techfinance.pessoal.api.user.domain.enums.Role;

@Component
public class AuthMapper
        extends MapperConverter<RegisterRequest, AuthResponse, AuthResult, UserRequest> {

    @Override
    protected UserRequest doToEntity(RegisterRequest request) {
        return toUserRequest(request);
    }

    @Override
    protected AuthResponse doToResponse(AuthResult result) {
        return new AuthResponse(
                result.getToken(),
                result.getName(),
                result.getUsername(),
                result.getRole()
        );
    }

    public UserRequest toUserRequest(RegisterRequest request) {
        return new UserRequest(
                request.name(),
                request.username(),
                request.password(),
                parseRole(request.role())
        );
    }

    public AuthResponse toAuthResponse(AuthResult result) {
        return toResponse(result);
    }

    private Role parseRole(String role) {
        if (role == null || role.isBlank()) {
            return Role.USER;
        }

        return Role.valueOf(role.toUpperCase());
    }
}