package com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidTelefonoValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTelefono {
    String message() default "El teléfono debe tener 9 dígitos, empezar con 9 y contener solo números";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

