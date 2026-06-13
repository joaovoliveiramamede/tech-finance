package com.techfinance.pessoal.api.account.adapter.in.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techfinance.pessoal.api.account.adapter.in.dto.request.TransactionRequest;
import com.techfinance.pessoal.api.account.adapter.in.dto.response.TransactionResponse;
import com.techfinance.pessoal.api.account.application.service.TransactionService;
import com.techfinance.pessoal.api.infra.shared.routes.ApiRoute;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = ApiRoute.API_V1 + ApiRoute.TRANSACTION)
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TransactionRestController {

    private final TransactionService service;

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> findAll() {
        return ResponseEntity.ok(
            service.findAll().stream()
                .map(TransactionResponse::from)
                .toList()
        );
    }

    @PostMapping("create")
    public ResponseEntity<TransactionResponse> create(@Valid @RequestBody TransactionRequest request) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(TransactionResponse.from(service.create(request)));
    }
}
