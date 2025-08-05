package com.ulasalle.gestorcitasconsultaclinicaspring.model;

import lombok.Getter;

@Getter
public enum EstadoUsuario {
    INACTIVO(0, "Usuario inactivo"),
    ACTIVO(1, "Usuario activo");

    private final int valor;
    private final String descripcion;

    EstadoUsuario(int valor, String descripcion) {
        this.valor = valor;
        this.descripcion = descripcion;
    }

    public static EstadoUsuario fromValor(int valor) {
        for (EstadoUsuario estado : EstadoUsuario.values()) {
            if (estado.valor == valor) {
                return estado;
            }
        }
        return null;
    }

    public static boolean esValido(int valor) {
        return fromValor(valor) != null;
    }
}
