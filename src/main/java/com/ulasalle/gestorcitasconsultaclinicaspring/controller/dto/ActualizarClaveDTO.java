package com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.validator.ValidClave;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ActualizarClaveDTO {
    @NotBlank(message = "La nueva clave es obligatoria")
    @ValidClave
    private String nuevaClave;
}
