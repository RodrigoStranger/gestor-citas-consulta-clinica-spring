package com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TextsValidator implements ConstraintValidator<ValidTexts, String> {
    @Override
    public void initialize(ValidTexts constraintAnnotation) {}
    @Override
    public boolean isValid(String nombreApellido, ConstraintValidatorContext context) {
        if (nombreApellido == null || nombreApellido.trim().isEmpty()) {
            return true;
        }
        if (nombreApellido.startsWith(" ") || nombreApellido.endsWith(" ")) {
            return false;
        }
        if (nombreApellido.contains("  ")) {
            return false;
        }
        String nombreTrimmed = nombreApellido.trim();
        if (nombreTrimmed.length() < 2 || nombreTrimmed.length() > 100) {
            return false;
        }
        if (!nombreTrimmed.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+$")) {
            return false;
        }
        String[] palabras = nombreTrimmed.split("\\s+");
        if (palabras.length == 0) {
            return false;
        }
        for (String palabra : palabras) {
            if (palabra.length() < 2) {
                return false;
            }
            if (!palabra.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ]+$")) {
                return false;
            }
        }
        return true;
    }
}
