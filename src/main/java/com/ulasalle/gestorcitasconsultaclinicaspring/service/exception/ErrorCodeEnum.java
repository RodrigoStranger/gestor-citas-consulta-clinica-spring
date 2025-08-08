package com.ulasalle.gestorcitasconsultaclinicaspring.service.exception;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {

    USUARIO_NOMBRE_EN_USO("USR_001", "El nombre del usuario ya se encuentra en uso."),
    USUARIO_DNI_EN_USO("USR_002", "El DNI del usuario ya se encuentra en uso."),
    USUARIO_CORREO_EN_USO("USR_003", "El correo del usuario ya se encuentra en uso."),
    USUARIO_FECHA_NACIMIENTO_INVALIDA("USR_004", "La fecha de nacimiento del usuario no es válida."),
    USUARIO_NO_ENCONTRADO("USR_005", "El usuario especificado no fue encontrado."),
    USUARIO_CLAVE_VACIA("USR_006", "La clave del usuario no puede estar vacía."),
    USUARIO_CLAVE_MUY_CORTA("USR_007", "La clave del usuario debe tener al menos 6 caracteres."),
    USUARIO_YA_TIENE_ROL("USR_008", "El usuario ya tiene el rol especificado asignado."),
    USUARIO_ID_REQUERIDO("USR_009", "El ID del usuario es obligatorio."),
    USUARIO_INACTIVO_NO_PUEDE_TENER_ROLES("USR_010", "No se pueden asignar roles a un usuario inactivo."),
    USUARIO_NO_TIENE_ROL("USR_011", "El usuario no tiene el rol especificado asignado."),
    USUARIO_DEBE_TENER_AL_MENOS_UN_ROL("USR_012", "El usuario debe tener al menos un rol asignado."),
    USUARIO_NO_PUEDE_QUITAR_ROL_POR_DEFECTO("USR_013", "No se puede quitar el rol por defecto del usuario."),
    USUARIO_NO_ES_ADMINISTRADOR("USR_014", "El usuario especificado no tiene el rol de administrador."),
    USUARIO_ESTADO_INVALIDO("USR_015", "El estado del usuario debe ser 0 (inactivo) o 1 (activo)."),
    USUARIO_NO_ES_PACIENTE("USR_016", "El usuario especificado no tiene el rol de paciente."),
    USUARIO_NO_ES_MEDICO("USR_017", "El usuario especificado no tiene el rol de médico."),

    MEDICO_NO_ENCONTRADO("MED_002", "El médico especificado no fue encontrado."),
    MEDICO_ESTADO_INVALIDO("MED_003", "El estado del médico debe ser 0 (inactivo) o 1 (activo)."),
    MEDICO_ESPECIALIDAD_INVALIDA("MED_004", "La especialidad del médico debe contener solo letras y espacios, tener entre 2 y 100 caracteres, y cada palabra debe tener al menos 2 letras."),

    RUTA_NO_ENCONTRADA("SYS_001", "Endpoint no existente o no disponible."),

    ROL_NO_ENCONTRADO("ROL_001", "El rol especificado no fue encontrado en el sistema."),
    ROL_TIPO_REQUERIDO("ROL_002", "El tipo de rol es obligatorio."),

    HORARIO_DUPLICADO("HOR_001", "Ya existe un horario igual para ese día y rango de horas."),
    HORARIO_INVALIDO("HOR_002", "El horario está fuera del rango permitido por la clínica."),
    HORARIO_NO_EXISTE("HOR_003", "El horario especificado no existe."),
    HORARIO_YA_ASIGNADO_MEDICO("HOR_004", "El horario ya está asignado a este médico.");

    private final String code;
    private final String message;

    ErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
