package com.ulasalle.gestorcitasconsultaclinicaspring.model;

public enum EstadoCita {
    PROCESO("Cita en proceso"),
    COMPLETADO("Cita completada"),
    CANCELADO("Cita cancelada");

    private final String descripcion;

    EstadoCita(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
