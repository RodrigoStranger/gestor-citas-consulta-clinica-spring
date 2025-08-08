package com.ulasalle.gestorcitasconsultaclinicaspring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "cita_medica")
@Getter
@Setter
public class CitaMedica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_cita;

    @ManyToOne
    @JoinColumn(name = "id_medico")
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "fecha_cita")
    private LocalDate fechaCita;

    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    @Column(name = "hora_fin")
    private LocalTime horaFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoCita estado;
}
