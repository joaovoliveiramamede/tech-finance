package com.techfinance.pessoal.desktop.service;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.techfinance.pessoal.desktop.dto.request.TransactionRequest;
import com.techfinance.pessoal.desktop.dto.response.TransactionResponse;
import com.techfinance.pessoal.desktop.infrastructure.http.ApiClient;

@Singleton
public class TransactionApiService {

    private final ApiClient apiClient;

    @Inject
    public TransactionApiService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public List<TransactionResponse> findAll() {
        return apiClient.getList("/transaction", TransactionResponse.class);
    }

    public TransactionResponse create(TransactionRequest request) {
        return apiClient.post("/transaction/create", request, TransactionResponse.class);
    }
}
