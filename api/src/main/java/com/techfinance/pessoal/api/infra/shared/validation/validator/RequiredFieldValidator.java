package com.techfinance.pessoal.api.infra.shared.validation.validator;

import com.techfinance.pessoal.api.infra.shared.validation.annotations.RequiredField;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class RequiredFieldValidator 
    implements ConstraintValidator<RequiredField, Object> {

    private String property;

    @Override
    public void initialize(RequiredField annotation) {
        this.property = annotation.property();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean valid = value != null;

        if (value instanceof String text) {
            valid = !text.trim().isEmpty();
        }

        if (!valid) {
            log.info("");
            context.disableDefaultConstraintViolation();

            context.buildConstraintViolationWithTemplate(
                "propriedade " + property + " não pode ser nula ou vazia"
            ).addConstraintViolation();
        }

        return valid;
    }
    
}
