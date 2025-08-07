package com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto;

import jakarta.validation.constraints.NotBlank;
import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.validator.ValidTexts;
import lombok.Data;

@Data
public class ActualizarEspecialidadDTO {
    @NotBlank(message = "La especialidad es obligatoria")
    @ValidTexts
    private String especialidad;
}

