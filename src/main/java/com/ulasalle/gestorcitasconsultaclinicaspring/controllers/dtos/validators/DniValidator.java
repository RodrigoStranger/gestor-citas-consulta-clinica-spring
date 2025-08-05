package com.ulasalle.gestorcitasconsultaclinicaspring.controllers.dtos.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DniValidator implements ConstraintValidator<ValidDni, String> {
    @Override
    public boolean isValid(String dni, ConstraintValidatorContext context) {
        if (dni == null || dni.trim().isEmpty()) {
            return false;
        }
        return dni.matches("^\\d{8}$");
    }
}