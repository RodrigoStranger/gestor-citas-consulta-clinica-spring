package com.ulasalle.gestorcitasconsultaclinicaspring.service;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.MedicoDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.Medico;

import java.util.List;
import java.util.Optional;

public interface IMedicoService {
    Medico crearMedico(MedicoDTO medicoDTO);
    List<Medico> listarMedicosHabilitados();
    List<Medico> listarMedicosDeshabilitados();
    Optional<Medico> buscarMedicoPorId(Long id);
    Medico obtenerMedicoPorId(Long id);
}
