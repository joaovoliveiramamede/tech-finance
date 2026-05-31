package com.techfinance.pessoal.api.infra.exception;

public class UnexpectedErrorException extends RuntimeException {
    
    public UnexpectedErrorException(String message) {
        super(message);
    }

    public UnexpectedErrorException(String message, Throwable error) {
        super(message, error);
    }

}
