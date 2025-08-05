package com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NombreApellidoValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidApellidoNombre {
    String message() default "El nombre/apellido debe contener solo letras y espacios, tener entre 2 y 100 caracteres, y cada palabra debe tener al menos 2 letras";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
