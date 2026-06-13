package com.techfinance.pessoal.desktop.infrastructure.http;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.techfinance.pessoal.desktop.config.ApiConfig;
import com.techfinance.pessoal.desktop.dto.response.ErrorResponse;
import com.techfinance.pessoal.desktop.infrastructure.session.SessionManager;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Singleton
public class ApiClient {

    private static final MediaType JSON = MediaType.parse("application/json");

    private final OkHttpClient client;
    private final ObjectMapper mapper;
    private final SessionManager session;
    private final String baseUrl;

    @Inject
    public ApiClient(
            OkHttpClient client,
            ObjectMapper mapper,
            SessionManager session,
            ApiConfig apiConfig) {

        this.client = client;
        this.mapper = mapper;
        this.session = session;
        this.baseUrl = apiConfig.getBaseUrl();
    }

    public <T> T get(String path, Class<T> responseType) {
        return execute(buildGet(path, true), responseType);
    }

    public <T> List<T> getList(String path, Class<T> elementType) {
        try {
            String body = executeRaw(buildGet(path, true));

            return mapper.readValue(
                body,
                mapper.getTypeFactory().constructCollectionType(List.class, elementType)
            );
        } catch (IOException exception) {
            throw new RuntimeException("erro ao ler resposta da API", exception);
        }
    }

    public <T> T postPublic(String path, Object requestBody, Class<T> responseType) {
        return execute(buildPost(path, requestBody, false), responseType);
    }

    public void postPublic(String path, Object requestBody) {
        executeRaw(buildPost(path, requestBody, false));
    }

    public <T> T post(String path, Object requestBody, Class<T> responseType) {
        return execute(buildPost(path, requestBody, true), responseType);
    }

    private Request buildGet(String path, boolean authenticated) {
        Request.Builder builder = new Request.Builder()
            .url(baseUrl + path)
            .get();

        if (authenticated) {
            builder.header("Authorization", "Bearer " + session.getToken());
        }

        return builder.build();
    }

    private Request buildPost(String path, Object requestBody, boolean authenticated) {
        try {
            String json = mapper.writeValueAsString(requestBody);
            RequestBody body = RequestBody.create(json, JSON);

            Request.Builder builder = new Request.Builder()
                .url(baseUrl + path)
                .post(body);

            if (authenticated) {
                builder.header("Authorization", "Bearer " + session.getToken());
            }

            return builder.build();
        } catch (IOException exception) {
            throw new RuntimeException("erro ao serializar requisição", exception);
        }
    }

    private <T> T execute(Request request, Class<T> responseType) {
        String body = executeRaw(request);

        if (responseType == Void.class) {
            return null;
        }

        try {
            return mapper.readValue(body, responseType);
        } catch (IOException exception) {
            throw new RuntimeException("erro ao ler resposta da API", exception);
        }
    }

    private String executeRaw(Request request) {
        try (Response response = client.newCall(request).execute()) {
            ResponseBody responseBody = response.body();
            String body = responseBody != null ? responseBody.string() : "";

            if (!response.isSuccessful()) {
                throw buildException(body, response.code());
            }

            return body;
        } catch (IOException exception) {
            throw new RuntimeException("erro de comunicação com a API", exception);
        }
    }

    private ApiException buildException(String body, int statusCode) {
        try {
            ErrorResponse error = mapper.readValue(body, ErrorResponse.class);
            return new ApiException(error.message(), statusCode);
        } catch (IOException exception) {
            return new ApiException("erro na API | status=" + statusCode, statusCode);
        }
    }
}
