package com.ulasalle.gestorcitasconsultaclinicaspring.service.impl;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.ActualizarEspecialidadDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.MedicoDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.AsignarHorarioMedicoDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.ActualizarMedicoDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.validator.TextsValidator;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.*;
import com.ulasalle.gestorcitasconsultaclinicaspring.repository.IMedicoJPARepository;
import com.ulasalle.gestorcitasconsultaclinicaspring.repository.IUsuarioJPARepository;
import com.ulasalle.gestorcitasconsultaclinicaspring.repository.IHorarioJPARepository;
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
    private final IHorarioJPARepository horarioRepository;

    public IMedicoServiceImpl(IMedicoJPARepository medicoRepository,
                             IUsuarioJPARepository usuarioRepository,
                             IUsuarioService usuarioService,
                             IHorarioJPARepository horarioRepository) {
        this.medicoRepository = medicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
        this.horarioRepository = horarioRepository;
    }

    @Override
    public Medico crearMedico(MedicoDTO medicoDTO) {
        validarEspecialidad(medicoDTO.getEspecialidad());
        Medico medicoExistente = medicoRepository.findByUsuario_Telefono(medicoDTO.getTelefono());
        if (medicoExistente != null) {
            throw new BusinessException(ErrorCodeEnum.TELEFONO_MEDICO_DUPLICADO);
        }
        Usuario usuarioExistente = usuarioRepository.findByTelefono(medicoDTO.getTelefono());
        if (usuarioExistente != null && EstadoUsuario.fromValor(usuarioExistente.getActivo()) == EstadoUsuario.INACTIVO) {
            throw new BusinessException(ErrorCodeEnum.MEDICO_DESHABILITADO_NO_PUEDE_AGENDAR);
        }
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
        return medicoRepository.findByUsuario_ActivoAndUsuario_Roles_Nombre(1, TipoRol.MEDICO);
    }

    @Override
    public List<Medico> listarMedicosDeshabilitados() {
        return medicoRepository.findByUsuario_ActivoAndUsuario_Roles_Nombre(0, TipoRol.MEDICO);
    }

    @Override
    public Medico obtenerMedicoPorId(Long id) {
        return medicoRepository.findById(id)
            .orElseThrow(() -> new BusinessException(ErrorCodeEnum.MEDICO_NO_ENCONTRADO));
    }

    @Override
    public Medico cambiarEstadoMedico(Long id, int nuevoEstado) {
        if (EstadoUsuario.fromValor(nuevoEstado) == null) {
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
    public Medico actualizarEspecialidad(Long idMedico, ActualizarEspecialidadDTO request) {
        Medico medico = medicoRepository.findById(idMedico)
            .orElseThrow(() -> new BusinessException(ErrorCodeEnum.MEDICO_NO_ENCONTRADO));
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

    @Override
    public Medico asignarHorarioAMedico(AsignarHorarioMedicoDTO dto) {
        Medico medico = medicoRepository.findById(dto.getIdMedico())
            .orElseThrow(() -> new BusinessException(ErrorCodeEnum.MEDICO_NO_ENCONTRADO));
        if (medico.getUsuario().getActivo() == 0) {
            throw new BusinessException(ErrorCodeEnum.MEDICO_DESHABILITADO_NO_PUEDE_ASIGNAR_HORARIO);
        }
        Horario horario = horarioRepository.findById(dto.getIdHorario())
            .orElseThrow(() -> new BusinessException(ErrorCodeEnum.HORARIO_NO_EXISTE));
        boolean yaAsignado = medico.getHorarios().stream()
            .anyMatch(h -> h.getId_horario().equals(dto.getIdHorario()));
        if (yaAsignado) {
            throw new BusinessException(ErrorCodeEnum.HORARIO_YA_ASIGNADO_MEDICO);
        }
        medico.getHorarios().add(horario);
        horario.getMedicos().add(medico);
        medicoRepository.save(medico);
        horarioRepository.save(horario);
        return medico;
    }

    @Override
    public Horario asignarHorarioAMedicoYRetornarHorario(AsignarHorarioMedicoDTO dto) {
        Medico medico = asignarHorarioAMedico(dto);
        if (medico != null && dto.getIdHorario() != null) {
            return medico.getHorarios().stream()
                .filter(h -> h.getId_horario().equals(dto.getIdHorario()))
                .findFirst().orElse(null);
        }
        return null;
    }

    @Override
    public Set<Horario> obtenerHorariosPorMedicoId(Long idMedico) {
        Medico medico = medicoRepository.findById(idMedico)
            .orElseThrow(() -> new BusinessException(ErrorCodeEnum.MEDICO_NO_ENCONTRADO));
        return medico.getHorarios();
    }

    @Override
    public Medico actualizarMedico(Long idMedico, ActualizarMedicoDTO dto) {
        Medico medico = medicoRepository.findById(idMedico)
            .orElseThrow(() -> new BusinessException(ErrorCodeEnum.MEDICO_NO_ENCONTRADO));
        Usuario usuario = medico.getUsuario();
        if (usuario.getActivo() == 0) {
            throw new BusinessException(ErrorCodeEnum.MEDICO_DESHABILITADO_NO_PUEDE_ACTUALIZAR);
        }
        Medico medicoExistente = medicoRepository.findByUsuario_Telefono(dto.getTelefono());
        if (medicoExistente != null && !medicoExistente.getId_medico().equals(medico.getId_medico())) {
            throw new BusinessException(ErrorCodeEnum.TELEFONO_MEDICO_DUPLICADO);
        }
        Medico medicoCorreo = medicoRepository.findByUsuario_Correo(dto.getCorreo());
        if (medicoCorreo != null && !medicoCorreo.getId_medico().equals(medico.getId_medico())) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_CORREO_EN_USO);
        }
        Medico medicoNombreApellido = medicoRepository.findByUsuario_NombreAndUsuario_Apellidos(dto.getNombre(), dto.getApellidos());
        if (medicoNombreApellido != null && !medicoNombreApellido.getId_medico().equals(medico.getId_medico())) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_NOMBRE_EN_USO);
        }
        usuario.setCorreo(dto.getCorreo());
        usuario.setTelefono(dto.getTelefono());
        usuario.setNombre(dto.getNombre());
        usuario.setApellidos(dto.getApellidos());
        usuarioRepository.save(usuario);
        medico.setEspecialidad(dto.getEspecialidad());
        return medicoRepository.save(medico);
    }
}