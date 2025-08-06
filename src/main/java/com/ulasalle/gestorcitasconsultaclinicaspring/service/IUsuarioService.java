package com.ulasalle.gestorcitasconsultaclinicaspring.service;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.UsuarioDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.TipoRol;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.Usuario;

import java.util.List;

public interface IUsuarioService {
    void actualizarClaveUsuario(Long idUsuario, String nuevaClave);
    Usuario crearUsuario(UsuarioDTO usuarioDTO);
    Usuario agregarRolAUsuario(Long idUsuario, TipoRol tipoRol);
    Usuario quitarRolAUsuario(Long idUsuario, TipoRol tipoRol);
    Usuario actualizarUsuario(Long idUsuario, UsuarioDTO usuarioDTO);
    void validarUsuarioParaActualizacion(Long idUsuario, UsuarioDTO usuarioDTO);
    List<Usuario> listarUsuariosPorRolYEstado(TipoRol tipoRol, int activo);
    Usuario obtenerAdministradorPorId(Long idUsuario);
    Usuario cambiarEstadoAdministrador(Long idUsuario, int nuevoEstado);
}
