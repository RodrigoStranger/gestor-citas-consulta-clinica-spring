package com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class MedicoDTO extends UsuarioDTO {
    private Long id;

    @NotEmpty(message = "El médico debe tener al menos una especialidad")
    private List<EspecialidadDTO> especialidades;

    private String numeroColegiado;
}