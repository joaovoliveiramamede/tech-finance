package com.techfinance.pessoal.api.infra.exception;

public class NotFoundErrorException extends RuntimeException {

    public NotFoundErrorException(String message) {
        super(message);
    }

    public NotFoundErrorException(String message, Throwable error) {
        super(message, error);
    }
}