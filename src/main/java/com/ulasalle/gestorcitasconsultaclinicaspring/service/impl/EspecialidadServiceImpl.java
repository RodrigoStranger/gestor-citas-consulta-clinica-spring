package com.ulasalle.gestorcitasconsultaclinicaspring.service.impl;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.EspecialidadDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.Especialidad;
import com.ulasalle.gestorcitasconsultaclinicaspring.repository.IEspecialidadJPARepository;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.IEspecialidadService;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.exception.BusinessException;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.exception.ErrorCodeEnum;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.util.TextNormalizationUtils;
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
        String nombreNormalizado = TextNormalizationUtils.normalizeText(especialidadDTO.getNombre());
        String descripcionNormalizada = TextNormalizationUtils.normalizeText(especialidadDTO.getDescripcion());
        boolean nombreExiste = especialidadRepository.findAll().stream()
                .anyMatch(especialidad -> {
                    String nombreExistente = TextNormalizationUtils.normalizeText(especialidad.getNombre());
                    return nombreExistente.equals(nombreNormalizado);
                });
        if (nombreExiste) {
            throw new BusinessException(ErrorCodeEnum.ESPECIALIDAD_NOMBRE_EN_USO);
        }
        boolean descripcionExiste = especialidadRepository.findAll().stream()
                .anyMatch(especialidad -> {
                    String descripcionExistente = TextNormalizationUtils.normalizeText(especialidad.getDescripcion());
                    return descripcionExistente.equals(descripcionNormalizada);
                });
        if (descripcionExiste) {
            throw new BusinessException(ErrorCodeEnum.ESPECIALIDAD_DESCRIPCION_EN_USO);
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
        String nombreNormalizado = TextNormalizationUtils.normalizeText(especialidadDTO.getNombre());
        String descripcionNormalizada = TextNormalizationUtils.normalizeText(especialidadDTO.getDescripcion());
        boolean nombreExiste = especialidadRepository.findAll().stream()
                .filter(esp -> !esp.getId().equals(id))
                .anyMatch(esp -> {
                    String nombreExistente = TextNormalizationUtils.normalizeText(esp.getNombre());
                    return nombreExistente.equals(nombreNormalizado);
                });

        if (nombreExiste) {
            throw new BusinessException(ErrorCodeEnum.ESPECIALIDAD_NOMBRE_EN_USO);
        }
        boolean descripcionExiste = especialidadRepository.findAll().stream()
                .filter(esp -> !esp.getId().equals(id))
                .anyMatch(esp -> {
                    String descripcionExistente = TextNormalizationUtils.normalizeText(esp.getDescripcion());
                    return descripcionExistente.equals(descripcionNormalizada);
                });
        if (descripcionExiste) {
            throw new BusinessException(ErrorCodeEnum.ESPECIALIDAD_DESCRIPCION_EN_USO);
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
