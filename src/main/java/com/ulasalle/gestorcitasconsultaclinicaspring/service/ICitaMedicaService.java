package com.ulasalle.gestorcitasconsultaclinicaspring.service;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.CrearCitaMedicaDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.CitaMedica;

import java.util.List;

public interface ICitaMedicaService {
    CitaMedica crearCitaMedica(CrearCitaMedicaDTO crearCitaMedicaDTO);

    List<CitaMedica> obtenerTodasLasCitas();

    CitaMedica obtenerCitaPorId(Long id);
}
