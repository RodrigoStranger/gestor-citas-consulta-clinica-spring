package com.ulasalle.gestorcitasconsultaclinicaspring.services.impls;

import com.ulasalle.gestorcitasconsultaclinicaspring.controllers.dtos.MedicoDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.models.Medico;
import com.ulasalle.gestorcitasconsultaclinicaspring.repositorys.IMedicoJPARepository;
import com.ulasalle.gestorcitasconsultaclinicaspring.services.IMedicoService;
import com.ulasalle.gestorcitasconsultaclinicaspring.services.exceptions.BusinessException;
import com.ulasalle.gestorcitasconsultaclinicaspring.services.exceptions.ErrorCodeEnum;
import com.ulasalle.gestorcitasconsultaclinicaspring.services.utils.TextNormalizationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class IMedicoServiceImpl implements IMedicoService {
    private final IMedicoJPARepository medicoRepository;

    public IMedicoServiceImpl(IMedicoJPARepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    @Override
    public Medico crearMedico(MedicoDTO medicoDTO) {
        String nombreNormalizado = TextNormalizationUtils.normalizeText(medicoDTO.getNombre());
        String apellidosNormalizado = TextNormalizationUtils.normalizeText(medicoDTO.getApellidos());

        // Verificar si ya existe un médico con el mismo nombre normalizado
        boolean nombreExiste = medicoRepository.findAll().stream()
                .anyMatch(medico -> TextNormalizationUtils.normalizeText(medico.getNombre()).equals(nombreNormalizado));

        if (nombreExiste) {
            throw new BusinessException(ErrorCodeEnum.MEDICO_NOMBRE_EN_USO);
        }

        // Verificar si ya existe un médico con los mismos apellidos normalizados
        boolean apellidosExiste = medicoRepository.findAll().stream()
                .anyMatch(medico -> TextNormalizationUtils.normalizeText(medico.getApellidos()).equals(apellidosNormalizado));

        if (apellidosExiste) {
            throw new BusinessException(ErrorCodeEnum.MEDICO_APELLIDOS_EN_USO);
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
        medico.setClaveAcceso(medicoDTO.getClaveAcceso());
        medico.setNombre(medicoDTO.getNombre());
        medico.setApellidos(medicoDTO.getApellidos());
        medico.setCorreo(medicoDTO.getCorreo());
        medico.setFechaNacimiento(medicoDTO.getFechaNacimiento());
        medico.setEspecialidad(medicoDTO.getEspecialidad());
        return medicoRepository.save(medico);
    }
}