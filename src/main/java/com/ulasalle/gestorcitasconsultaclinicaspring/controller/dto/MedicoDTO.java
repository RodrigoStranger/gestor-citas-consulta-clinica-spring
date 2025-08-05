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

    @NotEmpty(message = "El médico debe tener al menos una especialidad")
    private List<EspecialidadDTO> especialidades;
}