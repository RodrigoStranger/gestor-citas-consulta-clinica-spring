package com.ulasalle.gestorcitasconsultaclinicaspring.repository;

import com.ulasalle.gestorcitasconsultaclinicaspring.model.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface IHorarioJPARepository extends JpaRepository<Horario, Long> {
    List<Horario> findByTipoDiaSemanaAndHoraInicioAndHoraFin(com.ulasalle.gestorcitasconsultaclinicaspring.model.TipoDiaSemana tipoDiaSemana, LocalTime horaInicio, LocalTime horaFin);
}

