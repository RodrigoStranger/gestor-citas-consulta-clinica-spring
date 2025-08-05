package com.ulasalle.gestorcitasconsultaclinicaspring.services.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {

    MEDICO_NOMBRE_EN_USO("MED_001", "El nombre del médico ya se encuentra en uso."),
    MEDICO_DNI_EN_USO("MED_002", "El Dni del médico ya se encuentra en uso."),
    MEDICO_CORREO_EN_USO("MED_003", "El correo del médico ya se encuentra en uso."),
    MEDICO_FECHA_NACIMIENTO_INVALIDA("MED_004", "La fecha de nacimiento del médico no es válida.");

    private final String code;
    private final String message;

    ErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
