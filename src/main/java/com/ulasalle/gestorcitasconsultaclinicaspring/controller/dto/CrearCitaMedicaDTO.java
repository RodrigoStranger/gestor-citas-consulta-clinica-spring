package com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CrearCitaMedicaDTO {
    @NotBlank(message = "El ID del médico es obligatorio")
    private Long idMedico;

    @NotBlank(message = "El ID del usuario es obligatorio")
    private Long idUsuario;

    @NotBlank(message = "La fecha de la cita es obligatoria")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha debe tener formato YYYY-MM-DD y ser válida")
    private LocalDate fechaCita;

    @NotBlank(message = "La hora de inicio de la cita es obligatoria")
    @Pattern(regexp = "^(?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$", message = "La hora de fin debe tener formato HH:mm:ss y ser válida")
    private LocalTime horaInicio;

    @NotBlank(message = "La fecha hora de fin de la cita es obligatoria")
    @Pattern(regexp = "^(?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$", message = "La hora de fin debe tener formato HH:mm:ss y ser válida")
    private LocalTime horaFin;

    // El estado de la cita siempre será PENDIENTE al crearla
    // No se recibe por el DTO, se asigna en la lógica de negocio
}
