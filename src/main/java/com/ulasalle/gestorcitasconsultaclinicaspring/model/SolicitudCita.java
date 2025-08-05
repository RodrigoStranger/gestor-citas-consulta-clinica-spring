package com.ulasalle.gestorcitasconsultaclinicaspring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "solicitud_cita")
@Getter
@Setter
public class SolicitudCita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitud_cita")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "email")
    private String email;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;
    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDate.now();
    }
}