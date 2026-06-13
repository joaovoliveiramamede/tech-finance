package com.techfinance.pessoal.desktop.infrastructure.session;

import java.util.UUID;

import com.google.inject.Singleton;

@Singleton
public class SessionManager {

    private String token;
    private String username;
    private String name;
    private UUID userId;

    public void clear() {
        token = null;
        username = null;
        name = null;
        userId = null;
    }

    public boolean isAuthenticated() {
        return token != null && !token.isBlank();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
