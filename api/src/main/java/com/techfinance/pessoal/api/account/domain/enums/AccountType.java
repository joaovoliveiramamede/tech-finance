package com.techfinance.pessoal.api.account.domain.enums;

import java.util.Arrays;

import com.techfinance.pessoal.api.infra.exception.NotFoundErrorException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountType {

    CHECKING("checking", "Conta Corrente");

    private final String code;
    private final String description;

    public static AccountType fromCode(String code) {
        return Arrays.stream(values())
            .filter(type -> type.code.equalsIgnoreCase(code))
            .findFirst()
            .orElseThrow(() -> new NotFoundErrorException(
                "erro ao encontrar o tipo de conta"));
    }
}
