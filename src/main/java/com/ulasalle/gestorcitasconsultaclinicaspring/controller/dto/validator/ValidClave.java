package com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ClaveValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidClave {
    String message() default "La clave debe tener al menos 6 caracteres y no puede contener espacios ni comas";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}