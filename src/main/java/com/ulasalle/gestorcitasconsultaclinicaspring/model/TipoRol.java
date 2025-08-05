package com.ulasalle.gestorcitasconsultaclinicaspring.model;

import lombok.Getter;

@Getter
public enum TipoRol {
    ADMIN("Administrador del sistema"),
    MEDICO("Médico especialista"),
    PACIENTE("Paciente del sistema");

    private final String descripcion;

    TipoRol(String descripcion) {
        this.descripcion = descripcion;
    }
}
