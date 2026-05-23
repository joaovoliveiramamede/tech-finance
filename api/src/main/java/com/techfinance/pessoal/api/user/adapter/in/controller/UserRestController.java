package com.techfinance.pessoal.api.user.adapter.in.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techfinance.pessoal.api.user.adapter.in.dto.response.UserResponse;
import com.techfinance.pessoal.api.user.application.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {
    
    private final UserService service;
    @GetMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> findByUsername(
            @PathVariable String username
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.byUsername(username));
    }

}
