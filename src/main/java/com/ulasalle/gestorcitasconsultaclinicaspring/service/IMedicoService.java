package com.ulasalle.gestorcitasconsultaclinicaspring.service;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.MedicoDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.Medico;

import java.util.List;
import java.util.Set;

public interface IMedicoService {
    Medico crearMedico(MedicoDTO medicoDTO);
    List<Medico> listarMedicosHabilitados();
    List<Medico> listarMedicosDeshabilitados();
    Medico obtenerMedicoPorId(Long id);
    Medico cambiarEstadoMedico(Long id, int nuevoEstado);
    Medico actualizarMedico(Long idMedico, MedicoDTO medicoDTO);
    Set<String> obtenerTodasLasEspecialidades();
}
