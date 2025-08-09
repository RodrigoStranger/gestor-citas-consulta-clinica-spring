package com.ulasalle.gestorcitasconsultaclinicaspring.repository;

import com.ulasalle.gestorcitasconsultaclinicaspring.model.CitaMedica;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.Medico;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ICitaMedicaJPARepository extends JpaRepository<CitaMedica, Long> {
    boolean existsByMedicoAndFechaCitaAndHoraInicioBetween(Medico medico, LocalDate fechaCita, LocalTime horaInicio, LocalTime horaFin);

    List<CitaMedica> findByUsuario(Usuario usuario);
}
