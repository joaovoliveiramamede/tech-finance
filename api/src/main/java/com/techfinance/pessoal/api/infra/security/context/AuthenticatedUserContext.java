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
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@RequiredArgsConstructor
public class AuthenticatedUserContext {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("Obtendo usuário autenticado | authentication={}", authentication);
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedErrorException("usuário não autenticado");
        }

        String username = authentication.getName();
        log.debug("Obtendo usuário autenticado | username={}", username);

        if (username == null || username.isBlank() || "anonymousUser".equals(username)) {
            throw new UnauthorizedErrorException("usuário não autenticado");
        }

        log.debug("Obtendo usuário autenticado | buscando usuário no banco de dados | username={}", username);

        return userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundErrorException(
                "usuário não encontrado | username=" + username));
    }

    public UUID getCurrentUserId() {
        log.debug("Obtendo ID do usuário autenticado");
        return getCurrentUser().getId();
    }
}