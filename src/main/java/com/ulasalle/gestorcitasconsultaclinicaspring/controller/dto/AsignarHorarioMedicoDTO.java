package com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AsignarHorarioMedicoDTO {
    @NotBlank(message = "El ID del médico es obligatorio")
    private Long idMedico;
    @NotBlank(message = "El ID del horario es obligatorio")
    private Long idHorario;
}

