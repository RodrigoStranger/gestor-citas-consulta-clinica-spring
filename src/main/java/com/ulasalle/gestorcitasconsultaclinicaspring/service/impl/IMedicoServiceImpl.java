package com.ulasalle.gestorcitasconsultaclinicaspring.service.impl;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.MedicoDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.Medico;
import com.ulasalle.gestorcitasconsultaclinicaspring.repository.IMedicoJPARepository;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.IMedicoService;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.exception.BusinessException;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.exception.ErrorCodeEnum;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.util.TextNormalizationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class IMedicoServiceImpl implements IMedicoService {
    private final IMedicoJPARepository medicoRepository;
    public IMedicoServiceImpl(IMedicoJPARepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }
    @Override
    public Medico crearMedico(MedicoDTO medicoDTO) {
        String nombreCompleto = TextNormalizationUtils.normalizeText(
            medicoDTO.getNombre() + " " + medicoDTO.getApellidos()
        );
        boolean nombreCompletoExiste = medicoRepository.findAll().stream()
                .anyMatch(medico -> {
                    String nombreCompletoExistente = TextNormalizationUtils.normalizeText(
                        medico.getNombre() + " " + medico.getApellidos()
                    );
                    return nombreCompletoExistente.equals(nombreCompleto);
                });
        if (nombreCompletoExiste) {
            throw new BusinessException(ErrorCodeEnum.MEDICO_NOMBRE_EN_USO);
        }
        if (medicoRepository.existsByDni(medicoDTO.getDni())) {
            throw new BusinessException(ErrorCodeEnum.MEDICO_DNI_EN_USO);
        }
        if (medicoRepository.existsByCorreo(medicoDTO.getCorreo())) {
            throw new BusinessException(ErrorCodeEnum.MEDICO_CORREO_EN_USO);
        }
        if (medicoDTO.getFechaNacimiento() != null &&
                !medicoDTO.getFechaNacimiento().isBefore(LocalDate.now())) {
            throw new BusinessException(ErrorCodeEnum.MEDICO_FECHA_NACIMIENTO_INVALIDA);
        }
        Medico medico = new Medico();
        medico.setDni(medicoDTO.getDni());
        medico.setClave(medicoDTO.getClave());
        medico.setNombre(medicoDTO.getNombre());
        medico.setApellidos(medicoDTO.getApellidos());
        medico.setCorreo(medicoDTO.getCorreo());
        medico.setFechaNacimiento(medicoDTO.getFechaNacimiento());
        medico.setEspecialidad(medicoDTO.getEspecialidad());
        return medicoRepository.save(medico);
    }
    @Override
    public List<Medico> listarMedicosHabilitados() {
        return medicoRepository.findByActivo(1);
    }
    @Override
    public List<Medico> listarMedicosDeshabilitados() {
        return medicoRepository.findByActivo(0);
    }
}