package com.techfinance.pessoal.api.infra.exception;

public class BussinessErrorException extends RuntimeException {
    
    public BussinessErrorException(String message) {
        super(message);
    }

    public BussinessErrorException(String message, Throwable error) {
        super(message, error);
    }

}
