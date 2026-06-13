package com.techfinance.pessoal.api.auth.adapter.in.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techfinance.pessoal.api.auth.adapter.in.dto.request.LoginRequest;
import com.techfinance.pessoal.api.auth.adapter.in.dto.request.RegisterRequest;
import com.techfinance.pessoal.api.auth.adapter.in.dto.response.AuthResponse;
import com.techfinance.pessoal.api.auth.application.service.AuthService;
import com.techfinance.pessoal.api.infra.shared.routes.ApiRoute;
import com.techfinance.pessoal.api.user.adapter.in.dto.response.UserResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = ApiRoute.API_V1 + ApiRoute.AUTH)
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(
    name = "Autenticação",
    description = "Endpoints para cadastro e login de usuários"
)
public class AuthRestController {

    private final AuthService service;

    @Operation(
            summary = "Cadastrar usuário",
            description = "Cria um novo usuário e retorna um token JWT"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Usuário cadastrado com sucesso",
            content = @Content(schema = @Schema(implementation = AuthResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Erro de validação ou regra de negócio",
            content = @Content
    )
    @PostMapping("register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(AuthResponse.from(service.register(request)));
    }

    @Operation(
            summary = "Login",
            description = "Autentica usuário e retorna token JWT"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Login realizado com sucesso",
            content = @Content(schema = @Schema(implementation = AuthResponse.class))
    )
    @ApiResponse(
            responseCode = "401",
            description = "Credenciais inválidas",
            content = @Content
    )
    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(AuthResponse.from(service.login(request)));
    }
}
