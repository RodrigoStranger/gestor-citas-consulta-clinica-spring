package com.ulasalle.gestorcitasconsultaclinicaspring.service.impl;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.EspecialidadDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.MedicoDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.*;
import com.ulasalle.gestorcitasconsultaclinicaspring.repository.IEspecialidadJPARepository;
import com.ulasalle.gestorcitasconsultaclinicaspring.repository.IMedicoJPARepository;
import com.ulasalle.gestorcitasconsultaclinicaspring.repository.IRolJPARepository;
import com.ulasalle.gestorcitasconsultaclinicaspring.repository.IUsuarioJPARepository;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.IMedicoService;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.exception.BusinessException;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.exception.ErrorCodeEnum;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.util.TextNormalizationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class IMedicoServiceImpl implements IMedicoService {
    private final IMedicoJPARepository medicoRepository;
    private final IEspecialidadJPARepository especialidadRepository;
    private final IUsuarioJPARepository usuarioRepository;
    private final IRolJPARepository rolRepository;

    public IMedicoServiceImpl(IMedicoJPARepository medicoRepository,
                             IEspecialidadJPARepository especialidadRepository,
                             IUsuarioJPARepository usuarioRepository,
                             IRolJPARepository rolRepository) {
        this.medicoRepository = medicoRepository;
        this.especialidadRepository = especialidadRepository;
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    @Override
    public Medico crearMedico(MedicoDTO medicoDTO) {
        if (usuarioRepository.existsByDni(medicoDTO.getDni())) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_DNI_EN_USO);
        }
        if (usuarioRepository.existsByCorreo(medicoDTO.getCorreo())) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_CORREO_EN_USO);
        }
        String nombreCompleto = TextNormalizationUtils.normalizeText(
            medicoDTO.getNombre() + " " + medicoDTO.getApellidos()
        );
        boolean nombreCompletoExiste = usuarioRepository.findAll().stream()
                .anyMatch(usuario -> {
                    String nombreCompletoExistente = TextNormalizationUtils.normalizeText(
                        usuario.getNombre() + " " + usuario.getApellidos()
                    );
                    return nombreCompletoExistente.equals(nombreCompleto);
                });
        if (nombreCompletoExiste) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_NOMBRE_EN_USO);
        }
        if (medicoDTO.getFechaNacimiento() != null &&
                !medicoDTO.getFechaNacimiento().isBefore(LocalDate.now())) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_FECHA_NACIMIENTO_INVALIDA);
        }
        List<Long> especialidadIds = new ArrayList<>();
        for (EspecialidadDTO especialidadDTO : medicoDTO.getEspecialidades()) {
            Long especialidadId = especialidadDTO.getId();
            if (especialidadIds.contains(especialidadId)) {
                throw new BusinessException(ErrorCodeEnum.MEDICO_ESPECIALIDADES_DUPLICADAS);
            }
            especialidadIds.add(especialidadId);
        }
        Set<Especialidad> especialidades = new HashSet<>();
        for (EspecialidadDTO especialidadDTO : medicoDTO.getEspecialidades()) {
            Especialidad especialidad = especialidadRepository.findById(especialidadDTO.getId())
                .orElseThrow(() -> new BusinessException(ErrorCodeEnum.ESPECIALIDAD_NO_ENCONTRADA));
            especialidades.add(especialidad);
        }
        Rol rolMedico = rolRepository.findByNombre(TipoRol.MEDICO)
            .orElseThrow(() -> new BusinessException(ErrorCodeEnum.ROL_NO_ENCONTRADO));
        Usuario usuario = new Usuario();
        usuario.setDni(medicoDTO.getDni());
        usuario.setClave(medicoDTO.getClave());
        usuario.setNombre(medicoDTO.getNombre());
        usuario.setApellidos(medicoDTO.getApellidos());
        usuario.setCorreo(medicoDTO.getCorreo());
        usuario.setFechaNacimiento(medicoDTO.getFechaNacimiento());
        usuario.getRoles().add(rolMedico);
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        Medico medico = new Medico();
        medico.setUsuario(usuarioGuardado);
        medico.setEspecialidades(especialidades);
        return medicoRepository.save(medico);
    }

    @Override
    public List<Medico> listarMedicosHabilitados() {
        return medicoRepository.findMedicosHabilitados();
    }

    @Override
    public List<Medico> listarMedicosDeshabilitados() {
        return medicoRepository.findMedicosDeshabilitados();
    }
}