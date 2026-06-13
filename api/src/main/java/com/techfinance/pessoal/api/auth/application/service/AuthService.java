package com.techfinance.pessoal.api.auth.application.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.techfinance.pessoal.api.auth.adapter.in.dto.request.LoginRequest;
import com.techfinance.pessoal.api.auth.adapter.in.dto.request.RegisterRequest;
import com.techfinance.pessoal.api.auth.adapter.in.dto.response.AuthResponse;
import com.techfinance.pessoal.api.auth.application.mapper.AuthMapper;
import com.techfinance.pessoal.api.auth.domain.port.in.AuthCommand;
import com.techfinance.pessoal.api.auth.domain.port.in.AuthUseCase;
import com.techfinance.pessoal.api.auth.domain.port.out.result.AuthResult;
import com.techfinance.pessoal.api.infra.exception.BusinessErrorException;
import com.techfinance.pessoal.api.infra.security.jwt.JwtService;
import com.techfinance.pessoal.api.infra.shared.log.LogMessages;
import com.techfinance.pessoal.api.user.domain.port.in.UserUseCase;
import com.techfinance.pessoal.api.user.domain.port.out.result.UserResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {

    private final UserUseCase userUseCase;
    private final AuthMapper mapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public AuthResult register(RegisterRequest request)
            throws BusinessErrorException {

        try {

            log.debug(LogMessages.START, "registro", "autenticação");

            UserResult user = userUseCase.create(
                    mapper.toUserRequest(request));

            String token = jwtService.generateToken(
                    user.getUsername());

            log.debug(LogMessages.FINISH, "registro", "autenticação");

            return AuthResult.builder()
                .name(user.getName())
                .username(user.getUsername())
                .role(user.getRole().name())
                .token(token)
                .build();

        } catch (BusinessErrorException exception) {
            throw exception;
        } catch (Exception exception) {

            log.error(
                    LogMessages.BUSINESS_ERROR,
                    "registro",
                    AuthService.class.getSimpleName());

            throw new BusinessErrorException(
                    LogMessages.BUSINESS_ERROR_EXCEPTION_AUTH,
                    exception);
        }
    }

    @Override
    public AuthResult login(LoginRequest request)
            throws BusinessErrorException {

        try {

            log.debug(LogMessages.START, "login", "autenticação");

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.username(),
                            request.password()));

            UserResult user = userUseCase.byUsername(
                    request.username());

            String token = jwtService.generateToken(
                    request.username());

            log.debug(LogMessages.FINISH, "login", "autenticação");

            return AuthResult.builder()
                    .name(user.getName())
                    .username(user.getUsername())
                    .role(user.getRole().name())
                    .token(token)
                    .build();

        } catch (BusinessErrorException exception) {
            throw exception;
        } catch (Exception exception) {

            log.error(
                    LogMessages.BUSINESS_ERROR,
                    "login",
                    AuthService.class.getSimpleName());

            throw new BusinessErrorException(
                    LogMessages.BUSINESS_ERROR_EXCEPTION_AUTH,
                    exception);
        }
    }
}
