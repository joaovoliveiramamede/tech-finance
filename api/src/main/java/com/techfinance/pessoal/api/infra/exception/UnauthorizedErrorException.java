package com.techfinance.pessoal.api.infra.exception;

public class UnauthorizedErrorException extends RuntimeException {

    public UnauthorizedErrorException(String message) {
        super(message);
    }
}
