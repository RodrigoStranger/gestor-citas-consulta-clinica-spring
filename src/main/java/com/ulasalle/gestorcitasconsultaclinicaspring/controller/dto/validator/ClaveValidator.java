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
            return false;
        }
        return clave.trim().length() >= 6;
    }
}
