package com.techfinance.pessoal.desktop.dto.enums;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TransactionType {

    INCOME("income"),
    EXPENSE("expense");

    private final String code;

    TransactionType(String code) {
        this.code = code;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    @JsonCreator
    public static TransactionType fromCode(String code) {
        return Arrays.stream(values())
            .filter(type -> type.code.equalsIgnoreCase(code))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("tipo de transação inválido: " + code));
    }
}
