package com.techfinance.pessoal.api.user.application.exception;

public class UnexpectedErrorException extends RuntimeException {
    
    public UnexpectedErrorException(String message) {
        super(message);
    }

    public UnexpectedErrorException(String message, Throwable error) {
        super(message, error);
    }
}
