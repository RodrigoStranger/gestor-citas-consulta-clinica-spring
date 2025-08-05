package com.ulasalle.gestorcitasconsultaclinicaspring.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "especialidad")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Especialidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_especialidad")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "activa")
    private int activa = 1;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;
    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDate.now();
    }

    @ManyToMany(mappedBy = "especialidades", fetch = FetchType.LAZY)
    @JsonBackReference("medico-especialidades")
    private Set<Medico> medicos = new HashSet<>();

}
