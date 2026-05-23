package com.techfinance.pessoal.api.infra.security.exception;

public class NotFoundErrorException extends RuntimeException {
    
    public NotFoundErrorException(String message) {
        super(message);
    }

    public NotFoundErrorException(String message, Throwable error) {
        super(message, error);
    }

}