package com.ulasalle.gestorcitasconsultaclinicaspring.service.exception;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {

    MEDICO_NOMBRE_EN_USO("MED_001", "El nombre del médico ya se encuentra en uso por otro medico."),
    MEDICO_DNI_EN_USO("MED_002", "El Dni del médico ya se encuentra en uso por otro medico."),
    MEDICO_CORREO_EN_USO("MED_003", "El correo del médico ya se encuentra en uso por otro medico."),
    MEDICO_FECHA_NACIMIENTO_INVALIDA("MED_004", "La fecha de nacimiento del médico no es válida."),
    MEDICO_APELLIDOS_EN_USO("MED_005", "Los apellidos del médico ya se encuentran en uso por otro medico."),
    MEDICO_ESPECIALIDADES_DUPLICADAS("MED_006", "No se pueden agregar especialidades duplicadas para el mismo médico."),
    ESPECIALIDAD_NO_ENCONTRADA("ESP_001", "La especialidad especificada no fue encontrada."),
    ESPECIALIDAD_NOMBRE_EN_USO("ESP_002", "El nombre de la especialidad ya se encuentra en uso."),
    ESPECIALIDAD_DESCRIPCION_EN_USO("ESP_003", "La descripción de la especialidad ya se encuentra en uso.");

    private final String code;
    private final String message;

    ErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
