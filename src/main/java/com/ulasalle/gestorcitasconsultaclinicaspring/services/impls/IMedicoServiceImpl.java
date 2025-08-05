package com.ulasalle.gestorcitasconsultaclinicaspring.services.impls;

import com.ulasalle.gestorcitasconsultaclinicaspring.controllers.dtos.MedicoDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.models.Medico;
import com.ulasalle.gestorcitasconsultaclinicaspring.repositorys.IMedicoJPARepository;
import com.ulasalle.gestorcitasconsultaclinicaspring.services.IMedicoService;
import com.ulasalle.gestorcitasconsultaclinicaspring.services.exceptions.BusinessException;
import com.ulasalle.gestorcitasconsultaclinicaspring.services.exceptions.ErrorCodeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class IMedicoServiceImpl implements IMedicoService {
    private final IMedicoJPARepository medicoRepository;
    @Override
    public Medico crearMedico(MedicoDTO medicoDTO) {
        if (medicoRepository.existsByNombre(medicoDTO.getNombre())) {
            throw new BusinessException(ErrorCodeEnum.MEDICO_NOMBRE_EN_USO);
        }
        if (medicoRepository.existsByCodigoMedico(medicoDTO.getCoidigoMedico())) {
            throw new BusinessException(ErrorCodeEnum.MEDICO_CODIGO_EN_USO);
        }
        if (medicoRepository.existsByCorreo(medicoDTO.getCorreo())) {
            throw new BusinessException(ErrorCodeEnum.MEDICO_CORREO_EN_USO);
        }
        if (medicoDTO.getFechaNacimiento() != null &&
                !medicoDTO.getFechaNacimiento().isBefore(LocalDate.now())) {
            throw new BusinessException(ErrorCodeEnum.MEDICO_FECHA_NACIMIENTO_INVALIDA);
        }
        Medico medico = new Medico();
        medico.setCodigoMedico(medicoDTO.getCoidigoMedico());
        medico.setClaveAcceso(medicoDTO.getClaveAcceso());
        medico.setNombre(medicoDTO.getNombre());
        medico.setApellidos(medicoDTO.getApellidos());
        medico.setCorreo(medicoDTO.getCorreo());
        medico.setFechaNacimiento(medicoDTO.getFechaNacimiento());
        medico.setEspecialidad(medicoDTO.getEspecialidad());
        return medicoRepository.save(medico);
    }
}