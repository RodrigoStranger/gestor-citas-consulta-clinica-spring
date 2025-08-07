package com.ulasalle.gestorcitasconsultaclinicaspring.service.impl;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.ActualizarUsuarioDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.UsuarioDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.EstadoUsuario;
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
import java.util.List;
import java.util.stream.Collectors;

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
        NormalizedUsuarioFields fields = normalizeUsuarioFields(usuarioDTO);
        validarUsuarioDTO(usuarioDTO, fields.correo, fields.nombre, fields.apellidos);
        Usuario usuario = crearUsuarioDesdeDTO(usuarioDTO, fields.correo, fields.nombre, fields.apellidos);
        return usuarioRepository.save(usuario);
    }

    private NormalizedUsuarioFields normalizeUsuarioFields(UsuarioDTO usuarioDTO) {
        String correoNormalizado = TextNormalizationUtils.normalizeText(usuarioDTO.getCorreo());
        String nombreNormalizado = TextNormalizationUtils.normalizeText(usuarioDTO.getNombre());
        String apellidosNormalizados = TextNormalizationUtils.normalizeText(usuarioDTO.getApellidos());
        return new NormalizedUsuarioFields(correoNormalizado, nombreNormalizado, apellidosNormalizados);
    }

    private static class NormalizedUsuarioFields {
        String correo;
        String nombre;
        String apellidos;
        NormalizedUsuarioFields(String correo, String nombre, String apellidos) {
            this.correo = correo;
            this.nombre = nombre;
            this.apellidos = apellidos;
        }
    }

    private void validarUsuarioDTO(UsuarioDTO usuarioDTO, String correoNormalizado, String nombreNormalizado, String apellidosNormalizados) {
        if (usuarioRepository.existsByDni(usuarioDTO.getDni())) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_DNI_EN_USO);
        }
        if (usuarioRepository.existsByCorreo(correoNormalizado)) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_CORREO_EN_USO);
        }
        validarNombreCompletoUnico(nombreNormalizado, apellidosNormalizados, null);
        if (usuarioDTO.getFechaNacimiento() != null &&
                !usuarioDTO.getFechaNacimiento().isBefore(LocalDate.now())) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_FECHA_NACIMIENTO_INVALIDA);
        }
    }

    private Usuario crearUsuarioDesdeDTO(UsuarioDTO usuarioDTO, String correoNormalizado, String nombreNormalizado, String apellidosNormalizados) {
        Usuario usuario = new Usuario();
        usuario.setDni(usuarioDTO.getDni());
        usuario.setClave(usuarioDTO.getClave());
        usuario.setNombre(nombreNormalizado);
        usuario.setApellidos(apellidosNormalizados);
        usuario.setCorreo(correoNormalizado);
        usuario.setFechaNacimiento(usuarioDTO.getFechaNacimiento());
        return usuario;
    }

    @Override
    public Usuario agregarRolAUsuario(Long idUsuario, TipoRol tipoRol) {
        ValidacionRolData data = validarParametrosYObtenerEntidades(idUsuario, tipoRol);
        if (usuarioTieneRol(data.usuario, tipoRol)) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_YA_TIENE_ROL);
        }
        if (data.usuario.getActivo() == 0) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_INACTIVO_NO_PUEDE_TENER_ROLES);
        }

        if (data.usuario.getRoles().isEmpty()) {
            data.usuario.setRolPorDefecto(tipoRol);
        }

        data.usuario.getRoles().add(data.rol);
        return usuarioRepository.save(data.usuario);
    }

    @Override
    public Usuario quitarRolAUsuario(Long idUsuario, TipoRol tipoRol) {
        ValidacionRolData data = validarParametrosYObtenerEntidades(idUsuario, tipoRol);
        if (!usuarioTieneRol(data.usuario, tipoRol)) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_NO_TIENE_ROL);
        }
        if (data.usuario.getRoles().size() <= 1) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_DEBE_TENER_AL_MENOS_UN_ROL);
        }

        if (data.usuario.getRolPorDefecto() != null && data.usuario.getRolPorDefecto().equals(tipoRol)) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_NO_PUEDE_QUITAR_ROL_POR_DEFECTO);
        }

        removerRolDelUsuario(data.usuario, tipoRol);
        return usuarioRepository.save(data.usuario);
    }

    private Usuario actualizarUsuarioComun(Long idUsuario, String correo, String nombre, String apellidos) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new BusinessException(ErrorCodeEnum.USUARIO_NO_ENCONTRADO));
        String correoNormalizado = TextNormalizationUtils.normalizeText(correo);
        String nombreNormalizado = TextNormalizationUtils.normalizeText(nombre);
        String apellidosNormalizados = TextNormalizationUtils.normalizeText(apellidos);
        Usuario usuarioExistentePorCorreo = usuarioRepository.findByCorreo(correoNormalizado);
        if (usuarioExistentePorCorreo != null && !usuarioExistentePorCorreo.getId_usuario().equals(idUsuario)) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_CORREO_EN_USO);
        }
        validarNombreCompletoUnico(nombreNormalizado, apellidosNormalizados, idUsuario);
        usuario.setCorreo(correoNormalizado);
        usuario.setNombre(nombreNormalizado);
        usuario.setApellidos(apellidosNormalizados);
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario actualizarUsuario(Long idUsuario, ActualizarUsuarioDTO actualizarUsuarioDTO) {
        return actualizarUsuarioComun(idUsuario,
            actualizarUsuarioDTO.getCorreo(),
            actualizarUsuarioDTO.getNombre(),
            actualizarUsuarioDTO.getApellidos());
    }

    private void validarNombreCompletoUnico(String nombre, String apellidos, Long idUsuarioExcluir) {
        String nombreCompleto = nombre + " " + apellidos;
        boolean nombreCompletoExiste = usuarioRepository.findAll().stream()
                .filter(usuario -> idUsuarioExcluir == null || !idUsuarioExcluir.equals(usuario.getId_usuario()))
                .anyMatch(usuario -> {
                    String nombreCompletoExistente = usuario.getNombre() + " " + usuario.getApellidos();
                    return TextNormalizationUtils.normalizedEquals(nombreCompleto, nombreCompletoExistente);
                });
        if (nombreCompletoExiste) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_NOMBRE_EN_USO);
        }
    }

    private record ValidacionRolData(Usuario usuario, Rol rol) {}
    private ValidacionRolData validarParametrosYObtenerEntidades(Long idUsuario, TipoRol tipoRol) {
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
        return new ValidacionRolData(usuario, rol);
    }

    private boolean usuarioTieneRol(Usuario usuario, TipoRol tipoRol) {
        return usuario.getRoles().stream()
                .anyMatch(rol -> rol.getNombre() != null && rol.getNombre().equals(tipoRol));
    }

    @Override
    public List<Usuario> listarUsuariosPorRolYEstado(TipoRol tipoRol, int activo) {
        return usuarioRepository.findByActivo(activo).stream()
                .filter(usuario -> usuarioTieneRol(usuario, tipoRol))
                .collect(Collectors.toList());
    }

    @Override
    public Usuario obtenerAdministradorPorId(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new BusinessException(ErrorCodeEnum.USUARIO_NO_ENCONTRADO));

        if (!usuarioTieneRol(usuario, TipoRol.ADMIN)) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_NO_ES_ADMINISTRADOR);
        }

        return usuario;
    }

    @Override
    public Usuario cambiarEstadoAdministrador(Long idUsuario, int nuevoEstado) {
        if (EstadoUsuario.fromValor(nuevoEstado) == null) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_ESTADO_INVALIDO);
        }

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new BusinessException(ErrorCodeEnum.USUARIO_NO_ENCONTRADO));

        boolean esAdministrador = usuario.getRoles().stream()
                .anyMatch(rol -> rol.getNombre() != null && rol.getNombre().equals(TipoRol.ADMIN));

        if (!esAdministrador) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_NO_ES_ADMINISTRADOR);
        }

        usuario.setActivo(nuevoEstado);
        return usuarioRepository.save(usuario);
    }

    private void removerRolDelUsuario(Usuario usuario, TipoRol tipoRol) {
        usuario.getRoles().removeIf(r -> r.getNombre() != null && r.getNombre().equals(tipoRol));
    }

    @Override
    public List<Rol> obtenerRolesDeUsuario(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new BusinessException(ErrorCodeEnum.USUARIO_NO_ENCONTRADO));
        return new java.util.ArrayList<>(usuario.getRoles());
    }

    @Override
    public Usuario obtenerPacientePorId(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new BusinessException(ErrorCodeEnum.USUARIO_NO_ENCONTRADO));
        boolean esPaciente = usuario.getRoles().stream()
            .anyMatch(rol -> rol.getNombre() != null && rol.getNombre().equals(TipoRol.PACIENTE));
        if (!esPaciente) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_NO_ES_PACIENTE);
        }
        return usuario;
    }

    @Override
    public List<Usuario> listarPacientes() {
        return usuarioRepository.findAll().stream()
            .filter(usuario -> usuario.getRoles().stream()
                .anyMatch(rol -> rol.getNombre() != null && rol.getNombre().equals(TipoRol.PACIENTE)))
            .collect(Collectors.toList());
    }
}
