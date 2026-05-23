package com.techfinance.pessoal.api.user.application.mapper;

import com.techfinance.pessoal.api.user.adapter.in.dto.request.UserRequest;
import com.techfinance.pessoal.api.user.adapter.in.dto.response.UserResponse;
import com.techfinance.pessoal.api.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public User toEntity(UserRequest request) {
        return User.builder()
            .name(request.name())
            .username(request.username())
            .role(request.role())
            .password(request.password())
            .build();
    }

    public UserResponse toResponse(User entity) {
        return new UserResponse(
            entity.getId(),
            entity.getUsername(),
            entity.getPassword(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }


}
