package com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ClaveValidator implements ConstraintValidator<ValidClave, String> {

    @Override
    public void initialize(ValidClave constraintAnnotation) {}

    @Override
    public boolean isValid(String clave, ConstraintValidatorContext context) {
        // Si la clave es null o está vacía, considerarla válida
        // (dejar que @NotBlank maneje la validación de requerido)
        if (clave == null || clave.trim().isEmpty()) {
            return true;
        }

        // Validar que no contenga espacios ni comas
        if (clave.contains(" ") || clave.contains(",")) {
            return false;
        }

        // Solo validar longitud si la clave tiene contenido
        return clave.trim().length() >= 6;
    }
}
