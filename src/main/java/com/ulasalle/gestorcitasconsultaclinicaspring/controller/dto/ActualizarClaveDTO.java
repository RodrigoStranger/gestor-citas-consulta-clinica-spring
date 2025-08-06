package com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ActualizarClaveDTO {
    @NotBlank(message = "La nueva clave es obligatoria")
    @Size(min = 6, message = "La clave debe tener al menos 6 caracteres")
    private String nuevaClave;
}
