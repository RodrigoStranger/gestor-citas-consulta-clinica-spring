package com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ClaveValidator implements ConstraintValidator<ValidClave, String> {

    @Override
    public void initialize(ValidClave constraintAnnotation) {}

    @Override
    public boolean isValid(String clave, ConstraintValidatorContext context) {
        if (clave == null || clave.trim().isEmpty()) {
            return true;
        }
        if (clave.contains(" ") || clave.contains(",")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("La clave no puede contener espacios ni comas")
                   .addConstraintViolation();
            return false;
        }
        if (clave.trim().length() < 6) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("La clave debe tener al menos 6 caracteres")
                   .addConstraintViolation();
            return false;
        }
        return true;
    }
}
