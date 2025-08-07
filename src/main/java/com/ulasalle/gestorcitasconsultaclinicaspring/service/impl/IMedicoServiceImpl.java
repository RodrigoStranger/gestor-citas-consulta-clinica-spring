package com.ulasalle.gestorcitasconsultaclinicaspring.service.impl;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.MedicoDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.ActualizarMedicoDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.validator.TextsValidator;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.*;
import com.ulasalle.gestorcitasconsultaclinicaspring.repository.IMedicoJPARepository;
import com.ulasalle.gestorcitasconsultaclinicaspring.repository.IUsuarioJPARepository;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.IMedicoService;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.IUsuarioService;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.exception.BusinessException;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.exception.ErrorCodeEnum;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.util.TextNormalizationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class IMedicoServiceImpl implements IMedicoService {
    private final IMedicoJPARepository medicoRepository;
    private final IUsuarioJPARepository usuarioRepository;
    private final IUsuarioService usuarioService;

    public IMedicoServiceImpl(IMedicoJPARepository medicoRepository,
                             IUsuarioJPARepository usuarioRepository,
                             IUsuarioService usuarioService) {
        this.medicoRepository = medicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }

    @Override
    public Medico crearMedico(MedicoDTO medicoDTO) {
        validarEspecialidad(medicoDTO.getEspecialidad());
        Usuario usuario = usuarioService.crearUsuario(medicoDTO);
        usuario = usuarioService.agregarRolAUsuario(usuario.getId_usuario(), TipoRol.MEDICO);
        Medico medico = crearMedicoDesdeDTO(medicoDTO, usuario);
        return medicoRepository.save(medico);
    }

    private void validarEspecialidad(String especialidad) {
        TextsValidator validator = new TextsValidator();
        validator.initialize(null);
        if (!validator.isValid(especialidad, null)) {
            throw new BusinessException(ErrorCodeEnum.MEDICO_ESPECIALIDAD_INVALIDA);
        }
    }

    private Medico crearMedicoDesdeDTO(MedicoDTO medicoDTO, Usuario usuario) {
        Medico medico = new Medico();
        medico.setUsuario(usuario);
        medico.setEspecialidad(medicoDTO.getEspecialidad());
        return medico;
    }

    @Override
    public List<Medico> listarMedicosHabilitados() {
        return medicoRepository.findMedicosHabilitados(TipoRol.MEDICO);
    }

    @Override
    public List<Medico> listarMedicosDeshabilitados() {
        return medicoRepository.findMedicosDeshabilitados(TipoRol.MEDICO);
    }

    @Override
    public Medico obtenerMedicoPorId(Long id) {
        return medicoRepository.findById(id)
            .orElseThrow(() -> new BusinessException(ErrorCodeEnum.MEDICO_NO_ENCONTRADO));
    }

    @Override
    public Medico cambiarEstadoMedico(Long id, int nuevoEstado) {
        if (!EstadoUsuario.esValido(nuevoEstado)) {
            throw new BusinessException(ErrorCodeEnum.MEDICO_ESTADO_INVALIDO);
        }
        Medico medico = medicoRepository.findById(id)
            .orElseThrow(() -> new BusinessException(ErrorCodeEnum.MEDICO_NO_ENCONTRADO));
        Usuario usuario = medico.getUsuario();
        boolean esMedico = usuario.getRoles().stream()
            .anyMatch(rol -> rol.getNombre() != null && rol.getNombre().equals(TipoRol.MEDICO));
        if (!esMedico) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_NO_ES_MEDICO); // validacion extra xd
        }
        usuario.setActivo(nuevoEstado);
        usuarioRepository.save(usuario);
        return medico;
    }

    @Override
    public Medico actualizarMedico(Long idMedico, ActualizarMedicoDTO request) {
        Medico medico = medicoRepository.findById(idMedico)
            .orElseThrow(() -> new BusinessException(ErrorCodeEnum.MEDICO_NO_ENCONTRADO));
        Usuario usuario = medico.getUsuario();
        usuario.setCorreo(request.getCorreo());
        usuario.setNombre(request.getNombre());
        usuario.setApellidos(request.getApellidos());
        usuarioRepository.save(usuario);
        validarEspecialidad(request.getEspecialidad());
        medico.setEspecialidad(request.getEspecialidad());
        return medicoRepository.save(medico);
    }

    @Override
    public Set<String> obtenerTodasLasEspecialidades() {
        List<Medico> medicos = medicoRepository.findAll();
        return new HashSet<>(medicos.stream()
                .map(Medico::getEspecialidad)
                .filter(especialidad -> especialidad != null && !especialidad.trim().isEmpty())
                .collect(Collectors.toMap(
                    TextNormalizationUtils::normalizeText,
                    especialidad -> especialidad,
                    (existing, replacement) -> existing
                ))
                .values());
    }
}