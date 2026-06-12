package com.techfinance.pessoal.api.account.adapter.in.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techfinance.pessoal.api.account.adapter.in.dto.request.TransactionRequest;
import com.techfinance.pessoal.api.account.adapter.in.dto.response.TransactionResponse;
import com.techfinance.pessoal.api.account.application.service.TransactionService;
import com.techfinance.pessoal.api.infra.shared.routes.ApiRoute;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = ApiRoute.API_V1 + ApiRoute.TRANSACTION)
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TransactionRestController {
    
    private final TransactionService service;

    @PostMapping("create")
    public ResponseEntity<TransactionResponse> create(@Valid @RequestBody TransactionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(TransactionResponse.from(service.create(request)));
    }
    

}
