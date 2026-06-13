package com.techfinance.pessoal.api.account.adapter.in.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techfinance.pessoal.api.account.adapter.in.dto.request.CategoryRequest;
import com.techfinance.pessoal.api.account.adapter.in.dto.response.CategoryResponse;
import com.techfinance.pessoal.api.account.application.service.CategoryService;
import com.techfinance.pessoal.api.infra.shared.routes.ApiRoute;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = ApiRoute.API_V1 + ApiRoute.CATEGORY)
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CategoryRestController {
    
    private final CategoryService service;

    @PostMapping("create")
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CategoryRequest request) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(CategoryResponse.from(service.create(request)));
    }
    

}