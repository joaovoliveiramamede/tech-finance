package com.techfinance.pessoal.api.user.application.mapper;

import com.techfinance.pessoal.api.infra.shared.converter.MapperConverter;
import com.techfinance.pessoal.api.user.adapter.in.dto.request.UserRequest;
import com.techfinance.pessoal.api.user.adapter.in.dto.response.UserResponse;
import com.techfinance.pessoal.api.user.domain.model.User;
import com.techfinance.pessoal.api.user.domain.port.out.result.UserResult;

import org.springframework.stereotype.Component;

@Component
public class UserMapper 
    extends MapperConverter<UserRequest, UserResponse, UserResult, User> {

    @Override
    protected User doToEntity(UserRequest request) {
        return User.builder()
            .name(request.name())
            .username(request.username())
            .password(request.password())
            .role(request.role())
            .build();
    }

    @Override
    protected UserResult doToResult(User entity) {
        return UserResult.builder()
            .id(entity.getId())
            .name(entity.getName())
            .username(entity.getUsername())
            .role(entity.getRole())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .build();
    }

    @Override
    protected UserResponse doToResponse(UserResult result) {
        return new UserResponse(
            result.getId(),
            result.getName(),
            result.getUsername(),
            result.getRole(),
            result.getCreatedAt(),
            result.getUpdatedAt()
        );
    }

}