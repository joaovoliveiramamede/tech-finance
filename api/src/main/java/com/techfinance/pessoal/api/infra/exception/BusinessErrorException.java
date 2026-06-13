package com.techfinance.pessoal.api.infra.exception;

public class BusinessErrorException extends RuntimeException {

    public BusinessErrorException(String message) {
        super(message);
    }

    public BusinessErrorException(String message, Throwable error) {
        super(message, error);
    }
}
