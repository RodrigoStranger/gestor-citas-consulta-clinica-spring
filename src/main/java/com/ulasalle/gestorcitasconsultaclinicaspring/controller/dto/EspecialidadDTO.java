package com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.validator.ValidTexts;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EspecialidadDTO {
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @ValidTexts
    private String nombre;

    @NotBlank(message = "La descripcion es obligatoria")
    @ValidTexts
    private String descripcion;
}
