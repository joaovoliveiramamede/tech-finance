package com.techfinance.pessoal.api.user.domain.enums;

import java.util.Arrays;

import com.techfinance.pessoal.api.infra.exception.NotFoundErrorException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    USER("user", "Usuário"),
    ADMIN("admin", "Administrador");

    private final String code;
    private final String description;

    public static Role fromCode(String code) {
        return Arrays.stream(values())
            .filter(type -> type.code.equalsIgnoreCase(code))
            .findFirst()
            .orElseThrow(() -> new NotFoundErrorException(
                "erro ao encontrar o papel do usuário"));
    }
}
