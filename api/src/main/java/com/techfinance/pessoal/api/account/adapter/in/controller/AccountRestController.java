package com.techfinance.pessoal.api.account.adapter.in.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techfinance.pessoal.api.account.adapter.in.dto.request.AccountRequest;
import com.techfinance.pessoal.api.account.adapter.in.dto.response.AccountResponse;
import com.techfinance.pessoal.api.account.application.service.AccountService;
import com.techfinance.pessoal.api.infra.shared.routes.ApiRoute;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = ApiRoute.API_V1 + ApiRoute.ACCOUNT)
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AccountRestController {

    private final AccountService service;

    @PostMapping("create")
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody AccountRequest request) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(AccountResponse.from(service.create(request)));
    }

    @GetMapping("{id}")
    public ResponseEntity<AccountResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(AccountResponse.from(service.byId(id)));
    }
}
