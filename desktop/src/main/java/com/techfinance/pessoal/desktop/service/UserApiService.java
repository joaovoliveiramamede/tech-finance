package com.techfinance.pessoal.desktop.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.techfinance.pessoal.desktop.dto.response.UserResponse;
import com.techfinance.pessoal.desktop.infrastructure.http.ApiClient;

@Singleton
public class UserApiService {

    private final ApiClient apiClient;

    @Inject
    public UserApiService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public UserResponse findByUsername(String username) {
        return apiClient.get("/user/" + username, UserResponse.class);
    }
}
