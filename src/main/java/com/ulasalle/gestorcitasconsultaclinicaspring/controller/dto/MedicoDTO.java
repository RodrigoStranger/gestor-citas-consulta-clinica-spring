package com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto;
import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.validator.ValidTexts;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MedicoDTO extends UsuarioDTO {
    @NotBlank(message = "La especialidad es obligatoria")
    @ValidTexts
    private String especialidad;
}