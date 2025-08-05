package com.ulasalle.gestorcitasconsultaclinicaspring.service;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dtos.MedicoDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.Medico;

import java.util.List;

public interface IMedicoService {
    Medico crearMedico(MedicoDTO medicoDTO);
    List<Medico> listarMedicosHabilitados();
    List<Medico> listarMedicosDeshabilitados();
}
