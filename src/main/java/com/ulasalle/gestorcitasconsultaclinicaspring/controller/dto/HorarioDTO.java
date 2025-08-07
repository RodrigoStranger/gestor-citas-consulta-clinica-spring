package com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class HorarioDTO {

    @NotBlank(message = "El dia de la semana es obligatorio")
    @jakarta.validation.constraints.Pattern(regexp = "LUNES|MARTES|MIERCOLES|JUEVES|VIERNES|SABADO|DOMINGO", message = "El día de la semana debe ser un valor válido: LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO")
    private String tipoDiaSemana;

    @NotBlank(message = "La hora de inicio es obligatoria")
    @Pattern(regexp = "^(?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$", message = "La hora de fin debe tener formato HH:mm:ss y ser válida")
    private String horaInicio;

    @NotBlank(message = "La hora de fin es obligatoria")
    @Pattern(regexp = "^(?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$", message = "La hora de fin debe tener formato HH:mm:ss y ser válida")
    private String horaFin;

}
