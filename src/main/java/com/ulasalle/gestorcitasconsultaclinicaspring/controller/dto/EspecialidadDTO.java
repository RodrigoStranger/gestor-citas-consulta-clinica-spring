package com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.validator.ValidApellidoNombre;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EspecialidadDTO {
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @ValidApellidoNombre
    private String nombre;

    @NotBlank(message = "La descripcion es obligatoria")
    private String descripcion;
}
