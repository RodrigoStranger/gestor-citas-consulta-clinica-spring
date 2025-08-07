package com.ulasalle.gestorcitasconsultaclinicaspring.service;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.HorarioDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.Horario;

public interface IHorarioService {
    Horario crearHorario(HorarioDTO dto);
    Horario actualizarHorario(Long id, HorarioDTO dto);
}
