package com.techfinance.pessoal.desktop.service;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.techfinance.pessoal.desktop.dto.request.CategoryRequest;
import com.techfinance.pessoal.desktop.dto.response.CategoryResponse;
import com.techfinance.pessoal.desktop.infrastructure.http.ApiClient;

@Singleton
public class CategoryApiService {

    private final ApiClient apiClient;

    @Inject
    public CategoryApiService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public List<CategoryResponse> findAll() {
        return apiClient.getList("/category", CategoryResponse.class);
    }

    public CategoryResponse create(CategoryRequest request) {
        return apiClient.post("/category/create", request, CategoryResponse.class);
    }
}
