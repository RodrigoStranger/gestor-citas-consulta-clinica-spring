package com.ulasalle.gestorcitasconsultaclinicaspring.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "especialidades")
@Getter
@Setter
public class Especialidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_especialidad")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToMany(mappedBy = "especialidades", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Medico> medicos = new HashSet<>();

    @Column(name = "activa")
    private int activa = 1;
}
