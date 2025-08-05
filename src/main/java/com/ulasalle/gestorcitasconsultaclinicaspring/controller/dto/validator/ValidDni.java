package com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DniValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDni {
    String message() default "El dni debe ser un número válido de 8 dígitos";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}