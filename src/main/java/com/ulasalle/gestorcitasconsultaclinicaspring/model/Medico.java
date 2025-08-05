package com.ulasalle.gestorcitasconsultaclinicaspring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "medico")
@Getter
@Setter
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medico")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private Usuario usuario;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "medico_especialidad",
        joinColumns = @JoinColumn(name = "id_medico"),
        inverseJoinColumns = @JoinColumn(name = "id_especialidad")
    )
    private Set<Especialidad> especialidades = new HashSet<>();

}