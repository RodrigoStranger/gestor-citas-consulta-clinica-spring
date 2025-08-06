package com.ulasalle.gestorcitasconsultaclinicaspring.service.impl;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.MedicoDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.RolDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.validator.TextsValidator;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.*;
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
import java.util.List;

@Service
@Transactional
public class IMedicoServiceImpl implements IMedicoService {
    private final IMedicoJPARepository medicoRepository;
    private final IUsuarioJPARepository usuarioRepository;
    private final IRolJPARepository rolRepository;

    public IMedicoServiceImpl(IMedicoJPARepository medicoRepository,
                             IUsuarioJPARepository usuarioRepository,
                             IRolJPARepository rolRepository) {
        this.medicoRepository = medicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    @Override
    public Medico crearMedico(MedicoDTO medicoDTO) {
        validarUsuarioDTO(medicoDTO);
        validarEspecialidad(medicoDTO.getEspecialidad());
        Rol rolMedico = obtenerRolMedicoDesdeDTO();
        Usuario usuario = crearUsuarioDesdeDTO(medicoDTO, rolMedico);
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        Medico medico = crearMedicoDesdeDTO(medicoDTO, usuarioGuardado);
        return medicoRepository.save(medico);
    }

    @Override
    public Medico actualizarMedico(Long id, MedicoDTO medicoDTO) {
        Medico medico = obtenerMedicoPorId(id);
        validarEspecialidad(medicoDTO.getEspecialidad());
        Usuario usuario = medico.getUsuario();
        actualizarUsuarioDesdeDTO(usuario, medicoDTO);
        usuarioRepository.save(usuario);
        medico.setEspecialidad(medicoDTO.getEspecialidad());
        return medicoRepository.save(medico);
    }

    private void actualizarUsuarioDesdeDTO(Usuario usuario, MedicoDTO medicoDTO) {
        usuario.setNombre(medicoDTO.getNombre());
        usuario.setApellidos(medicoDTO.getApellidos());
        usuario.setCorreo(medicoDTO.getCorreo());
        usuario.setFechaNacimiento(medicoDTO.getFechaNacimiento());
        if (medicoDTO.getClave() != null && !medicoDTO.getClave().trim().isEmpty()) {
            usuario.setClave(medicoDTO.getClave());
        }
    }

    private void validarUsuarioDTO(MedicoDTO medicoDTO) {
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
    }

    private void validarEspecialidad(String especialidad) {
        TextsValidator validator = new TextsValidator();
        validator.initialize(null);
        if (!validator.isValid(especialidad, null)) {
            throw new BusinessException(ErrorCodeEnum.MEDICO_ESPECIALIDAD_INVALIDA);
        }
    }

    private Usuario crearUsuarioDesdeDTO(MedicoDTO medicoDTO, Rol rolMedico) {
        Usuario usuario = new Usuario();
        usuario.setDni(medicoDTO.getDni());
        usuario.setClave(medicoDTO.getClave());
        usuario.setNombre(medicoDTO.getNombre());
        usuario.setApellidos(medicoDTO.getApellidos());
        usuario.setCorreo(medicoDTO.getCorreo());
        usuario.setFechaNacimiento(medicoDTO.getFechaNacimiento());
        usuario.getRoles().add(rolMedico);
        return usuario;
    }

    private Rol obtenerRolMedicoDesdeDTO() {
        RolDTO rolDTO = new RolDTO();
        rolDTO.setNombre(TipoRol.MEDICO);
        return rolRepository.findByNombre(rolDTO.getNombre())
            .orElseThrow(() -> new BusinessException(ErrorCodeEnum.ROL_NO_ENCONTRADO));
    }

    private Medico crearMedicoDesdeDTO(MedicoDTO medicoDTO, Usuario usuario) {
        Medico medico = new Medico();
        medico.setUsuario(usuario);
        medico.setEspecialidad(medicoDTO.getEspecialidad());
        return medico;
    }

    @Override
    public List<Medico> listarMedicosHabilitados() {
        return medicoRepository.findByUsuarioActivo(1);
    }

    @Override
    public List<Medico> listarMedicosDeshabilitados() {
        return medicoRepository.findByUsuarioActivo(0);
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
        usuario.setActivo(nuevoEstado);
        usuarioRepository.save(usuario);
        return medico;
    }
}