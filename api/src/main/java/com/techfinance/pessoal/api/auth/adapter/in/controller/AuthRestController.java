package com.techfinance.pessoal.api.auth.adapter.in.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techfinance.pessoal.api.auth.adapter.in.dto.request.LoginRequest;
import com.techfinance.pessoal.api.auth.adapter.in.dto.response.AuthResponse;
import com.techfinance.pessoal.api.auth.application.service.AuthService;
import com.techfinance.pessoal.api.user.adapter.in.dto.request.UserRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRestController {
    
    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(
        @RequestBody UserRequest request
    ) {
        service.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
        @RequestBody LoginRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
            service.login(request)
        );
    }

}
