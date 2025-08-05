package com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TextsValidator implements ConstraintValidator<ValidTexts, String> {

    @Override
    public void initialize(ValidTexts constraintAnnotation) {}

    @Override
    public boolean isValid(String nombreApellido, ConstraintValidatorContext context) {
        // Si el campo es null o está vacío, lo consideramos válido
        // La validación de campo requerido la maneja @NotBlank
        if (nombreApellido == null || nombreApellido.isEmpty()) {
            return true;
        }

        // NO hacer trim aquí - validar el string original
        // Validar que no empiece o termine con espacio
        if (nombreApellido.startsWith(" ") || nombreApellido.endsWith(" ")) {
            return false;
        }

        // Validar que no tenga espacios múltiples consecutivos
        if (nombreApellido.contains("  ")) {
            return false;
        }

        String nombreTrimmed = nombreApellido.trim();

        // Validar longitud total (después de trim para longitud real)
        if (nombreTrimmed.length() < 2 || nombreTrimmed.length() > 100) {
            return false;
        }

        // Validar que solo contenga letras, espacios y caracteres especiales del español
        if (!nombreTrimmed.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+$")) {
            return false;
        }

        // Dividir en palabras y validar cada una
        String[] palabras = nombreTrimmed.split("\\s+");

        // Debe tener al menos una palabra
        if (palabras.length == 0) {
            return false;
        }

        // Cada palabra debe tener al menos 2 caracteres
        for (String palabra : palabras) {
            if (palabra.length() < 2) {
                return false;
            }

            // Validar que cada palabra contenga solo letras
            if (!palabra.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ]+$")) {
                return false;
            }
        }

        return true;
    }
}
