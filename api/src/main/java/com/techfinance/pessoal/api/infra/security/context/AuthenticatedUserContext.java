package com.techfinance.pessoal.api.infra.security.context;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.techfinance.pessoal.api.infra.exception.NotFoundErrorException;
import com.techfinance.pessoal.api.infra.exception.UnauthorizedErrorException;
import com.techfinance.pessoal.api.user.domain.model.User;
import com.techfinance.pessoal.api.user.domain.port.out.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticatedUserContext {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedErrorException("usuário não autenticado");
        }

        String username = authentication.getName();

        if (username == null || username.isBlank() || "anonymousUser".equals(username)) {
            throw new UnauthorizedErrorException("usuário não autenticado");
        }

        return userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundErrorException(
                "usuário não encontrado | username=" + username));
    }

    public UUID getCurrentUserId() {
        return getCurrentUser().getId();
    }
}
