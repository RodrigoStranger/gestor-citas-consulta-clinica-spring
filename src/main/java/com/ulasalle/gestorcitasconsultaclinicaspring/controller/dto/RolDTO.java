package com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto;

import com.ulasalle.gestorcitasconsultaclinicaspring.model.TipoRol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RolDTO {

    @NotNull(message = "El tipo de rol es obligatorio")
    private TipoRol nombre;

    @NotBlank(message = "La descripción del rol es obligatoria")
    private String descripcion;
}
