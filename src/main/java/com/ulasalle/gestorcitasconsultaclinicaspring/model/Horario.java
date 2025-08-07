package com.ulasalle.gestorcitasconsultaclinicaspring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "horario")
@Getter
@Setter
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_horario")
    private Long id_horario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_dia_semana")
    private TipoDiaSemana tipoDiaSemana;

    @Column(name = "hora_inicio")
    private String horaInicio;

    @Column(name = "hora_fin")
    private  String horaFin;

    @ManyToMany
    @JoinTable(
        name = "medico_horario",
        joinColumns = @JoinColumn(name = "id_horario"),
        inverseJoinColumns = @JoinColumn(name = "id_medico")
    )
    private Set<Medico> medicos = new HashSet<>();
}
