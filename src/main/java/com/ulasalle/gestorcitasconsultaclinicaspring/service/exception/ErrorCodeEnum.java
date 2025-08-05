package com.ulasalle.gestorcitasconsultaclinicaspring.service.exception;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {

    USUARIO_NOMBRE_EN_USO("USR_001", "El nombre del usuario ya se encuentra en uso."),
    USUARIO_DNI_EN_USO("USR_002", "El DNI del usuario ya se encuentra en uso."),
    USUARIO_CORREO_EN_USO("USR_003", "El correo del usuario ya se encuentra en uso."),
    USUARIO_FECHA_NACIMIENTO_INVALIDA("USR_004", "La fecha de nacimiento del usuario no es válida."),

    MEDICO_ESPECIALIDADES_DUPLICADAS("MED_001", "No se pueden agregar especialidades duplicadas para el mismo médico."),
    MEDICO_NO_ENCONTRADO("MED_002", "El médico especificado no fue encontrado."),
    MEDICO_ESTADO_INVALIDO("MED_003", "El estado del médico debe ser 0 (inactivo) o 1 (activo)."),

    RUTA_NO_ENCONTRADA("SYS_001", "Endpoint no existente o no disponible."),

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
