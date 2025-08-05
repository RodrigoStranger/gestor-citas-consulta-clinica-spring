package com.ulasalle.gestorcitasconsultaclinicaspring.controllers.dtos;

import com.ulasalle.gestorcitasconsultaclinicaspring.controllers.dtos.validators.ValidDni;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class MedicoDTO {
    private Long id;

    @NotBlank(message = "El dni es obligatorio")
    @ValidDni
    private String dni;

    @NotBlank(message = "La clave de acceso es obligatoria")
    private String clave;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellidos;

    @Email(message = "El formato del correo no es válido")
    @NotBlank(message = "El correo es obligatorio")
    private String correo;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser anterior a la fecha actual")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "La especialidad para un medico es obligatoria")
    private String especialidad;
}