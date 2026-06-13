package com.techfinance.pessoal.api.infra.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.techfinance.pessoal.api.infra.exception.BusinessErrorException;
import com.techfinance.pessoal.api.infra.exception.NotFoundErrorException;
import com.techfinance.pessoal.api.infra.exception.UnauthorizedErrorException;
import com.techfinance.pessoal.api.infra.exception.UnexpectedErrorException;

import lombok.extern.log4j.Log4j2;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedErrorException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedErrorException exception) {
        log.warn("acesso não autorizado | message={}", exception.getMessage());
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(ErrorResponse.of(exception.getMessage()));
    }

    @ExceptionHandler(NotFoundErrorException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundErrorException exception) {
        log.warn("recurso não encontrado | message={}", exception.getMessage());
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse.of(exception.getMessage()));
    }

    @ExceptionHandler(BusinessErrorException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessErrorException exception) {
        log.warn("erro de negócio | message={}", exception.getMessage());
        return ResponseEntity
            .status(HttpStatus.UNPROCESSABLE_ENTITY)
            .body(ErrorResponse.of(exception.getMessage()));
    }

    @ExceptionHandler(UnexpectedErrorException.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(UnexpectedErrorException exception) {
        log.error("erro inesperado | message={}", exception.getMessage(), exception);
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.of(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
            .findFirst()
            .map(error -> error.getDefaultMessage())
            .orElse("dados inválidos");

        log.warn("erro de validação | message={}", message);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.of(message));
    }
}
