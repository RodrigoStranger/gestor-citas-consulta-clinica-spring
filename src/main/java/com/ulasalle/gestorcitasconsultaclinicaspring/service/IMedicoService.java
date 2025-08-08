package com.ulasalle.gestorcitasconsultaclinicaspring.service;


import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.ActualizarEspecialidadDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.AsignarHorarioMedicoDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.MedicoDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.Horario;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.Medico;

import java.util.List;
import java.util.Set;

public interface IMedicoService {
    Medico crearMedico(MedicoDTO medicoDTO);
    List<Medico> listarMedicosHabilitados();
    List<Medico> listarMedicosDeshabilitados();
    Medico obtenerMedicoPorId(Long id);
    Medico cambiarEstadoMedico(Long id, int nuevoEstado);
    Medico actualizarEspecialidad(Long idMedico, ActualizarEspecialidadDTO request);
    Set<String> obtenerTodasLasEspecialidades();
    Medico asignarHorarioAMedico(AsignarHorarioMedicoDTO dto);
    Horario asignarHorarioAMedicoYRetornarHorario(AsignarHorarioMedicoDTO dto);
    Set<Horario> obtenerHorariosPorMedicoId(Long idMedico);
}
