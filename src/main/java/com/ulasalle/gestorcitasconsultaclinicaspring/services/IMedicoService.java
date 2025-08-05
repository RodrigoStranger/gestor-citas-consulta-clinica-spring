package com.ulasalle.gestorcitasconsultaclinicaspring.services;

import com.ulasalle.gestorcitasconsultaclinicaspring.controllers.dtos.MedicoDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.models.Medico;

public interface IMedicoService {
    Medico crearMedico(MedicoDTO medicoDTO);
}
