package com.techfinance.pessoal.desktop.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ApiConfig {

    private static final String DEFAULT_BASE_URL = "http://localhost:8080/api/v1";

    private final String baseUrl;

    public ApiConfig() {
        Properties properties = new Properties();

        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input != null) {
                properties.load(input);
            }
        } catch (IOException exception) {
            throw new RuntimeException("erro ao carregar application.properties", exception);
        }

        this.baseUrl = properties.getProperty("api.base-url", DEFAULT_BASE_URL);
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
