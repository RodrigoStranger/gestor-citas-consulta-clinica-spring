package com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.validator.ValidTexts;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ActualizarMedicoDTO {
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    private String correo;

    @NotBlank(message = "El nombre es obligatorio")
    @ValidTexts
    private String nombre;

    @NotBlank(message = "Los apellidos son obligatorios")
    @ValidTexts
    private String apellidos;

    @NotBlank(message = "La especialidad es obligatoria")
    @ValidTexts
    private String especialidad;
}
