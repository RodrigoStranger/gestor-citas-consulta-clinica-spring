package com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.validator.ValidTelefono;
import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.validator.ValidTexts;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ActualizarMedicoDTO {
    @Email(message = "El formato del correo no es válido")
    private String correo;

    @NotBlank(message = "El teléfono es obligatorio")
    @ValidTelefono
    private String telefono;

    @NotBlank(message = "El nombre es obligatorio")
    @ValidTexts
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @ValidTexts
    private String apellidos;

    @NotBlank(message = "La especialidad es obligatoria")
    private String especialidad;
}

