package com.ulasalle.gestorcitasconsultaclinicaspring.service.impl;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.EspecialidadDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.Especialidad;
import com.ulasalle.gestorcitasconsultaclinicaspring.repository.IEspecialidadJPARepository;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.IEspecialidadService;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.exception.BusinessException;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.exception.ErrorCodeEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EspecialidadServiceImpl implements IEspecialidadService {

    private final IEspecialidadJPARepository especialidadRepository;

    public EspecialidadServiceImpl(IEspecialidadJPARepository especialidadRepository) {
        this.especialidadRepository = especialidadRepository;
    }

    @Override
    public Especialidad crearEspecialidad(EspecialidadDTO especialidadDTO) {
        if (especialidadRepository.existsByNombre(especialidadDTO.getNombre())) {
            throw new BusinessException(ErrorCodeEnum.ESPECIALIDAD_NOMBRE_EN_USO);
        }

        Especialidad especialidad = new Especialidad();
        especialidad.setNombre(especialidadDTO.getNombre());
        especialidad.setDescripcion(especialidadDTO.getDescripcion());

        return especialidadRepository.save(especialidad);
    }

    @Override
    public List<Especialidad> listarEspecialidades() {
        return especialidadRepository.findAll();
    }

    @Override
    public Especialidad buscarPorId(Long id) {
        return especialidadRepository.findById(id)
            .orElseThrow(() -> new BusinessException(ErrorCodeEnum.ESPECIALIDAD_NO_ENCONTRADA));
    }

    @Override
    public Especialidad actualizarEspecialidad(Long id, EspecialidadDTO especialidadDTO) {
        Especialidad especialidad = buscarPorId(id);

        if (!especialidad.getNombre().equals(especialidadDTO.getNombre()) &&
            especialidadRepository.existsByNombre(especialidadDTO.getNombre())) {
            throw new BusinessException(ErrorCodeEnum.ESPECIALIDAD_NOMBRE_EN_USO);
        }

        especialidad.setNombre(especialidadDTO.getNombre());
        especialidad.setDescripcion(especialidadDTO.getDescripcion());

        return especialidadRepository.save(especialidad);
    }

    @Override
    public void eliminarEspecialidad(Long id) {
        Especialidad especialidad = buscarPorId(id);
        especialidadRepository.delete(especialidad);
    }
}
