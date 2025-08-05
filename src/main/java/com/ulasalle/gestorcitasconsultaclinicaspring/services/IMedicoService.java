package com.ulasalle.gestorcitasconsultaclinicaspring.services;

import com.ulasalle.gestorcitasconsultaclinicaspring.controllers.dtos.MedicoDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.models.Medico;

import java.util.List;

public interface IMedicoService {
    Medico crearMedico(MedicoDTO medicoDTO);
    List<Medico> listarMedicosHabilitados();
    List<Medico> listarMedicosDeshabilitados();
}
