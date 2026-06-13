package com.techfinance.pessoal.api.auth.domain.port.out.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResult {
    private String name;
    private String username;
    private String role;
    private String token;
}