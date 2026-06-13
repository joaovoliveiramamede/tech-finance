package com.techfinance.pessoal.desktop.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.techfinance.pessoal.desktop.dto.request.LoginRequest;
import com.techfinance.pessoal.desktop.dto.request.RegisterRequest;
import com.techfinance.pessoal.desktop.dto.response.AuthResponse;
import com.techfinance.pessoal.desktop.infrastructure.http.ApiClient;
import com.techfinance.pessoal.desktop.infrastructure.session.SessionManager;

@Singleton
public class AuthService {

    private final ApiClient apiClient;
    private final SessionManager session;
    private final UserApiService userApiService;

    @Inject
    public AuthService(
            ApiClient apiClient,
            SessionManager session,
            UserApiService userApiService) {

        this.apiClient = apiClient;
        this.session = session;
        this.userApiService = userApiService;
    }

    public void register(RegisterRequest request) {
        AuthResponse response = apiClient.postPublic(
            "/auth/register",
            request,
            AuthResponse.class
        );

        establishSession(response);
    }

    public void login(LoginRequest request) {
        AuthResponse response = apiClient.postPublic(
            "/auth/login",
            request,
            AuthResponse.class
        );

        establishSession(response);
    }

    public void logout() {
        session.clear();
    }

    public boolean isAuthenticated() {
        return session.isAuthenticated();
    }

    public String getUsername() {
        return session.getUsername();
    }

    public String getName() {
        return session.getName();
    }

    public String getToken() {
        return session.getToken();
    }

    private void establishSession(AuthResponse response) {
        session.setToken(response.token());
        session.setUsername(response.username());
        session.setName(response.name());

        var user = userApiService.findByUsername(response.username());
        session.setUserId(user.id());
    }
}
