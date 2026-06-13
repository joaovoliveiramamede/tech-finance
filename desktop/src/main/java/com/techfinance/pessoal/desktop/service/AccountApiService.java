package com.techfinance.pessoal.desktop.service;

import java.math.BigDecimal;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.techfinance.pessoal.desktop.dto.request.AccountRequest;
import com.techfinance.pessoal.desktop.dto.response.AccountResponse;
import com.techfinance.pessoal.desktop.infrastructure.http.ApiClient;
import com.techfinance.pessoal.desktop.infrastructure.session.SessionManager;

@Singleton
public class AccountApiService {

    private final ApiClient apiClient;
    private final SessionManager session;

    @Inject
    public AccountApiService(ApiClient apiClient, SessionManager session) {
        this.apiClient = apiClient;
        this.session = session;
    }

    public List<AccountResponse> findAll() {
        return apiClient.getList("/account", AccountResponse.class);
    }

    public AccountResponse create(AccountRequest request) {
        return apiClient.post("/account/create", request, AccountResponse.class);
    }

    public AccountResponse createAccount(String name, BigDecimal balance) {
        return create(buildRequest(name, balance));
    }

    public AccountRequest buildRequest(String name, BigDecimal balance) {
        return new AccountRequest(
            name,
            balance,
            com.techfinance.pessoal.desktop.dto.enums.AccountType.CHECKING,
            session.getUserId()
        );
    }
}
