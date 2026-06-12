package com.techfinance.pessoal.desktop.service;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.techfinance.pessoal.desktop.dto.request.LoginRequest;
import com.techfinance.pessoal.desktop.dto.request.RegisterRequest;
import com.techfinance.pessoal.desktop.dto.response.AuthResponse;

import okhttp3.*;

@Singleton
public class AuthService {

    private static final String BASE_URL =
            "http://localhost:8080/auth";

    private final OkHttpClient client;
    private final ObjectMapper mapper;
    private String token;
    private String username;

    @Inject
    public AuthService(
            OkHttpClient client,
            ObjectMapper mapper
    ) {
        this.client = client;
        this.mapper = mapper;
    }

    public void register(RegisterRequest request) {

        try {

            String json = mapper.writeValueAsString(request);

            RequestBody body = RequestBody.create(
                    json,
                    MediaType.parse("application/json")
            );

            Request httpRequest = new Request.Builder()
                    .url(BASE_URL + "/register")
                    .post(body)
                    .build();

            try (Response response =
                         client.newCall(httpRequest).execute()) {

                if (!response.isSuccessful()) {
                    throw new RuntimeException(
                            "Erro: " + response.code()
                    );
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void login(LoginRequest request) {

        try {

            String json = mapper.writeValueAsString(request);

            RequestBody body = RequestBody.create(
                    json,
                    MediaType.parse("application/json")
            );

            Request httpRequest = new Request.Builder()
                    .url(BASE_URL + "/login")
                    .post(body)
                    .build();

            try (Response response =
                         client.newCall(httpRequest).execute()) {

                if (!response.isSuccessful()) {
                    throw new RuntimeException(
                            "Username ou senha inválidos."
                    );
                }

                ResponseBody responseBody = response.body();

                if (responseBody == null) {
                    throw new RuntimeException("Resposta de login inválida.");
                }

                AuthResponse authResponse = mapper.readValue(
                        responseBody.string(),
                        AuthResponse.class
                );

                token = authResponse.token();
                username = authResponse.username();
            }

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean isAuthenticated() {
        return token != null && !token.isBlank();
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public void logout() {
        token = null;
        username = null;
    }
}
