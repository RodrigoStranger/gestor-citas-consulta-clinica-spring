package com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CrearCitaMedicaDTO {
    @NotNull(message = "El ID del médico es obligatorio")
    private Long idMedico;

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long idUsuario;

    @NotNull(message = "La fecha de la cita es obligatoria")
    @FutureOrPresent(message = "La fecha de la cita no puede ser una fecha pasada")
    private LocalDate fechaCita;

    @NotNull(message = "La hora de inicio de la cita es obligatoria")
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin de la cita es obligatoria")
    private LocalTime horaFin;


}
