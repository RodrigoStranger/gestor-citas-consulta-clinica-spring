package com.ulasalle.gestorcitasconsultaclinicaspring.controllers.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import java.time.LocalDate;

@Data
public class MedicoDTO {
    private Long id;

    @NotBlank(message = "El codigo de un medico es obligatorio")
    private String coidigoMedico;

    @NotBlank(message = "La clave de acceso de un medico es obligatoria")
    private String claveAcceso;

    @NotBlank(message = "El nombre de un medico es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido de un medico es obligatorio")
    private String apellidos;

    @Email(message = "El formato del correo no es válido")
    private String correo;

    @NotBlank(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "La especialidad para un medico es obligatoria")
    private String especialidad;
}