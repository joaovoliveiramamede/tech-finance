package com.techfinance.pessoal.api.infra.shared.validation.annotations;

import java.lang.annotation.*;

import com.techfinance.pessoal.api.infra.shared.validation.validator.RequiredFieldValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = RequiredFieldValidator.class)
@Target({
    ElementType.FIELD,
    ElementType.PARAMETER,
    ElementType.RECORD_COMPONENT
})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface RequiredField {
    
    String property();
    String message() default "campo obrigatorio inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}