package com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.validator.ValidDni;
import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.validator.ValidTexts;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class MedicoDTO {
    private Long id;

    @NotBlank(message = "El dni es obligatorio")
    @ValidDni
    private String dni;

    @NotBlank(message = "La clave de acceso es obligatoria")
    private String clave;

    @NotBlank(message = "El nombre es obligatorio")
    @ValidTexts
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @ValidTexts
    private String apellidos;

    @Email(message = "El formato del correo no es válido")
    @NotBlank(message = "El correo es obligatorio")
    private String correo;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser anterior a la fecha actual")
    private LocalDate fechaNacimiento;

    @NotEmpty(message = "El médico debe tener al menos una especialidad")
    private List<EspecialidadDTO> especialidades;
}