package com.techfinance.pessoal.desktop.service;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.techfinance.pessoal.desktop.dto.request.RegisterRequest;

import okhttp3.*;

public class AuthService {

    private static final String BASE_URL =
            "http://localhost:8080/auth";

    private final OkHttpClient client;
    private final ObjectMapper mapper;

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
}