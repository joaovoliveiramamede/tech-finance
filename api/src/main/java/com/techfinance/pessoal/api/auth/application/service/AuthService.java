package com.techfinance.pessoal.api.auth.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.techfinance.pessoal.api.auth.adapter.in.dto.request.LoginRequest;
import com.techfinance.pessoal.api.auth.domain.port.in.AuthCommand;
import com.techfinance.pessoal.api.infra.security.jwt.JwtService;
import com.techfinance.pessoal.api.user.adapter.in.dto.request.UserRequest;
import com.techfinance.pessoal.api.user.application.exception.BussinessErrorException;
import com.techfinance.pessoal.api.user.application.service.UserService;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthService implements AuthCommand {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public void register(UserRequest request) throws BussinessErrorException {
        try {
            log.info("iniciando registro de usuário");
            userService.create(request);
        } catch (Exception exception) {
            throw new BussinessErrorException("error ao registrar usuário", exception);
        }
    }

    @Override
    public String login(LoginRequest request) throws BussinessErrorException {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );

            return jwtService.generateToken(request.username());
        } catch (Exception exception) {
            throw new BussinessErrorException("error ao logar usuário", exception);
        }
    }

}