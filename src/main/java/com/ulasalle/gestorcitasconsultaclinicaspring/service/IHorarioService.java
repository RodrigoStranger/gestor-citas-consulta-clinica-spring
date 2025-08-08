package com.ulasalle.gestorcitasconsultaclinicaspring.service;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.HorarioDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.Horario;

import java.util.List;

public interface IHorarioService {
    Horario crearHorario(HorarioDTO dto);
    Horario actualizarHorario(Long id, HorarioDTO dto);
    List<Horario> listarHorarios();
    Horario obtenerHorarioPorId(Long id);
    void eliminarHorarioPorId(Long id);
}
