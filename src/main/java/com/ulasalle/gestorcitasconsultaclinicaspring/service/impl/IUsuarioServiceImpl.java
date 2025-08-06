package com.ulasalle.gestorcitasconsultaclinicaspring.service.impl;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.UsuarioDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.Rol;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.TipoRol;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.Usuario;
import com.ulasalle.gestorcitasconsultaclinicaspring.repository.IRolJPARepository;
import com.ulasalle.gestorcitasconsultaclinicaspring.repository.IUsuarioJPARepository;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.IUsuarioService;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.exception.BusinessException;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.exception.ErrorCodeEnum;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.util.TextNormalizationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class IUsuarioServiceImpl implements IUsuarioService {
    private final IUsuarioJPARepository usuarioRepository;
    private final IRolJPARepository rolRepository;

    public IUsuarioServiceImpl(IUsuarioJPARepository usuarioRepository, IRolJPARepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    @Override
    public void actualizarClaveUsuario(Long idUsuario, String nuevaClave) {
        if (nuevaClave == null || nuevaClave.trim().isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_CLAVE_VACIA);
        }

        if (nuevaClave.length() < 6) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_CLAVE_MUY_CORTA);
        }

        Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new BusinessException(ErrorCodeEnum.USUARIO_NO_ENCONTRADO));

        usuario.setClave(nuevaClave.trim());
        usuarioRepository.save(usuario);
    }

    @Override
    public Usuario crearUsuario(UsuarioDTO usuarioDTO) {
        validarUsuarioDTO(usuarioDTO);
        Usuario usuario = crearUsuarioDesdeDTO(usuarioDTO);
        return usuarioRepository.save(usuario);
    }

    private void validarUsuarioDTO(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.existsByDni(usuarioDTO.getDni())) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_DNI_EN_USO);
        }
        if (usuarioRepository.existsByCorreo(usuarioDTO.getCorreo())) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_CORREO_EN_USO);
        }
        String nombreCompleto = TextNormalizationUtils.normalizeText(
            usuarioDTO.getNombre() + " " + usuarioDTO.getApellidos()
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
        if (usuarioDTO.getFechaNacimiento() != null &&
                !usuarioDTO.getFechaNacimiento().isBefore(LocalDate.now())) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_FECHA_NACIMIENTO_INVALIDA);
        }
    }

    private Usuario crearUsuarioDesdeDTO(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setDni(usuarioDTO.getDni());
        usuario.setClave(usuarioDTO.getClave());
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellidos(usuarioDTO.getApellidos());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setFechaNacimiento(usuarioDTO.getFechaNacimiento());
        return usuario;
    }

    @Override
    public Usuario agregarRolAUsuario(Long idUsuario, TipoRol tipoRol) {
        // Validar parámetros de entrada
        if (idUsuario == null) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_ID_REQUERIDO);
        }
        if (tipoRol == null) {
            throw new BusinessException(ErrorCodeEnum.ROL_TIPO_REQUERIDO);
        }

        Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new BusinessException(ErrorCodeEnum.USUARIO_NO_ENCONTRADO));

        Rol rol = rolRepository.findByNombre(tipoRol)
            .orElseThrow(() -> new BusinessException(ErrorCodeEnum.ROL_NO_ENCONTRADO));

        // Verificar si el usuario ya tiene este rol (validación mejorada)
        boolean yaTieneRol = usuario.getRoles().stream()
            .anyMatch(r -> r.getNombre() != null && r.getNombre().equals(tipoRol));

        if (yaTieneRol) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_YA_TIENE_ROL);
        }

        // Validación adicional: verificar si el usuario está activo
        if (usuario.getActivo() == 0) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_INACTIVO_NO_PUEDE_TENER_ROLES);
        }

        usuario.getRoles().add(rol);
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario quitarRolAUsuario(Long idUsuario, TipoRol tipoRol) {
        // Validar parámetros de entrada
        if (idUsuario == null) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_ID_REQUERIDO);
        }
        if (tipoRol == null) {
            throw new BusinessException(ErrorCodeEnum.ROL_TIPO_REQUERIDO);
        }

        Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new BusinessException(ErrorCodeEnum.USUARIO_NO_ENCONTRADO));

        // Verificar si el usuario tiene el rol que se quiere quitar
        boolean tieneRol = usuario.getRoles().stream()
            .anyMatch(r -> r.getNombre() != null && r.getNombre().equals(tipoRol));

        if (!tieneRol) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_NO_TIENE_ROL);
        }

        // Validar que el usuario tenga al menos un rol después de quitar este
        if (usuario.getRoles().size() <= 1) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_DEBE_TENER_AL_MENOS_UN_ROL);
        }

        // Remover el rol del usuario
        usuario.getRoles().removeIf(r -> r.getNombre() != null && r.getNombre().equals(tipoRol));

        return usuarioRepository.save(usuario);
    }
}
