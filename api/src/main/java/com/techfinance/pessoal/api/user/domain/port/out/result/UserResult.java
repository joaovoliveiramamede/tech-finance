package com.techfinance.pessoal.api.user.domain.port.out.result;

import java.time.Instant;
import java.util.UUID;

import com.techfinance.pessoal.api.user.domain.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResult {
    private UUID id;
    private String name;
    private String username;
    private Role role;
    private Instant createdAt;
    private Instant updatedAt;
}
