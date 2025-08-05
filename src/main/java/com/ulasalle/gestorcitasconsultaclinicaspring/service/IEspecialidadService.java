package com.ulasalle.gestorcitasconsultaclinicaspring.service;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.EspecialidadDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.Especialidad;

import java.util.List;

public interface IEspecialidadService {

    Especialidad crearEspecialidad(EspecialidadDTO especialidadDTO);

    List<Especialidad> listarEspecialidades();

    Especialidad buscarPorId(Long id);

    Especialidad actualizarEspecialidad(Long id, EspecialidadDTO especialidadDTO);

    void eliminarEspecialidad(Long id);
}
