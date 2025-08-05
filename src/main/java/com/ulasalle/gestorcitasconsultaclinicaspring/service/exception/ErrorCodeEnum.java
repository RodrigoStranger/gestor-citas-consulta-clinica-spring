package com.ulasalle.gestorcitasconsultaclinicaspring.service.exception;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {

    USUARIO_NOMBRE_EN_USO("USU_001", "El nombre del médico ya se encuentra en uso por otro usuario."),
    USUARIO_DNI_EN_USO("USU_002", "El Dni del médico ya se encuentra en uso por otro usuario."),
    USUARIO_CORREO_EN_USO("USU_003", "El correo del médico ya se encuentra en uso por otro usuario."),
    USUARIO_FECHA_NACIMIENTO_INVALIDA("USU_004", "La fecha de nacimiento del usuario no es válida."),
    USUARIO_APELLIDOS_EN_USO("USU_005", "Los apellidos del médico ya se encuentran en uso por otro usuario."),

    MEDICO_ESPECIALIDADES_DUPLICADAS("MED_001", "No se pueden agregar especialidades duplicadas para el mismo médico."),

    ESPECIALIDAD_NO_ENCONTRADA("ESP_001", "La especialidad especificada no fue encontrada."),
    ESPECIALIDAD_NOMBRE_EN_USO("ESP_002", "El nombre de la especialidad ya se encuentra en uso."),
    ESPECIALIDAD_DESCRIPCION_EN_USO("ESP_003", "La descripción de la especialidad ya se encuentra en uso."),
    ROL_NO_ENCONTRADO("ROL_001", "El rol especificado no fue encontrado en el sistema.");

    private final String code;
    private final String message;

    ErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
