package com.techfinance.pessoal.api.account.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import com.techfinance.pessoal.api.infra.security.exception.NotFoundErrorException;

@Getter
@RequiredArgsConstructor
public enum TransactionType {

    INCOME("income", "Entrada"),
    EXPENSE("expense", "Despesa");

    private final String code;
    private final String description;

    public static TransactionType fromCode(String code) {
        return Arrays.stream(values())
                .filter(type -> type.code.equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new NotFoundErrorException(
                        "erro ao encontrar o tipo de transacao"));
    }
}