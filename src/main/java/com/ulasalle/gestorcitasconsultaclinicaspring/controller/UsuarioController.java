package com.ulasalle.gestorcitasconsultaclinicaspring.controller;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.ActualizarClaveDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.ActualizarUsuarioDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.UsuarioDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.Rol;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.TipoRol;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.Usuario;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.IUsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final IUsuarioService usuarioService;

    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/salud")
    public ResponseEntity<?> estado() {
        ResponseWrapper<String> response = ResponseWrapper.success("Servicio activo", "El servicio de usuarios está funcionando correctamente");
        return response.toResponseEntity();
    }

    @PutMapping("/{idUsuario}/clave")
    public ResponseEntity<?> actualizarClaveUsuario(@PathVariable Long idUsuario, @Valid @RequestBody ActualizarClaveDTO actualizarClaveDTO) {
        usuarioService.actualizarClaveUsuario(idUsuario, actualizarClaveDTO.getNuevaClave());
        ResponseWrapper<Void> response = ResponseWrapper.success(null, "Clave de usuario actualizada exitosamente");
        return response.toResponseEntity();
    }

    @PutMapping("/{idUsuario}/rol/{nombreRol}")
    public ResponseEntity<?> agregarRolAUsuario(@PathVariable Long idUsuario, @PathVariable String nombreRol) {
        TipoRol tipoRol = TipoRol.valueOf(nombreRol);
        Usuario usuario = usuarioService.agregarRolAUsuario(idUsuario, tipoRol);
        ResponseWrapper<Usuario> response = ResponseWrapper.success(usuario, "Rol asignado al usuario exitosamente");
        return response.toResponseEntity();
    }

    @DeleteMapping("/{idUsuario}/rol/{nombreRol}")
    public ResponseEntity<?> quitarRolAUsuario(@PathVariable Long idUsuario, @PathVariable String nombreRol) {
        TipoRol tipoRol = TipoRol.valueOf(nombreRol);
        Usuario usuario = usuarioService.quitarRolAUsuario(idUsuario, tipoRol);
        ResponseWrapper<Usuario> response = ResponseWrapper.success(usuario, "Rol removido del usuario exitosamente");
        return response.toResponseEntity();
    }

    @PostMapping("/pacientes")
    public ResponseEntity<?> crearPaciente(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioService.crearUsuario(usuarioDTO);
        usuario = usuarioService.agregarRolAUsuario(usuario.getId_usuario(), TipoRol.PACIENTE);
        ResponseWrapper<Usuario> response = ResponseWrapper.success(usuario, "Paciente creado exitosamente");
        return response.toResponseEntity();
    }

    @PostMapping("/administradores")
    public ResponseEntity<?> crearAdministrador(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioService.crearUsuario(usuarioDTO);
        usuario = usuarioService.agregarRolAUsuario(usuario.getId_usuario(), TipoRol.ADMIN);
        ResponseWrapper<Usuario> response = ResponseWrapper.success(usuario, "Administrador creado exitosamente");
        return response.toResponseEntity();
    }

    @GetMapping("/administradores/habilitados")
    public ResponseEntity<?> listarAdministradoresHabilitados() {
        List<Usuario> administradores = usuarioService.listarUsuariosPorRolYEstado(TipoRol.ADMIN, 1);
        ResponseWrapper<List<Usuario>> response = ResponseWrapper.success(administradores, "Administradores habilitados obtenidos exitosamente");
        return response.toResponseEntity();
    }

    @GetMapping("/administradores/deshabilitados")
    public ResponseEntity<?> listarAdministradoresDeshabilitados() {
        List<Usuario> administradores = usuarioService.listarUsuariosPorRolYEstado(TipoRol.ADMIN, 0);
        ResponseWrapper<List<Usuario>> response = ResponseWrapper.success(administradores, "Administradores deshabilitados obtenidos exitosamente");
        return response.toResponseEntity();
    }

    @GetMapping("/administradores/{idUsuario}")
    public ResponseEntity<?> obtenerAdministradorPorId(@PathVariable Long idUsuario) {
        Usuario administrador = usuarioService.obtenerAdministradorPorId(idUsuario);
        ResponseWrapper<Usuario> response = ResponseWrapper.success(administrador, "Administrador encontrado exitosamente");
        return response.toResponseEntity();
    }

    @PutMapping("/administradores/{idUsuario}/estado/{estado}")
    public ResponseEntity<?> cambiarEstadoAdministrador(@PathVariable Long idUsuario, @PathVariable int estado) {
        Usuario administrador = usuarioService.cambiarEstadoAdministrador(idUsuario, estado);
        String mensaje = estado == 0 ? "Administrador deshabilitado exitosamente" : "Administrador habilitado exitosamente";
        ResponseWrapper<Usuario> response = ResponseWrapper.success(administrador, mensaje);
        return response.toResponseEntity();
    }

    @GetMapping("/{idUsuario}/roles")
    public ResponseEntity<?> obtenerRolesDeUsuario(@PathVariable Long idUsuario) {
        List<Rol> roles = usuarioService.obtenerRolesDeUsuario(idUsuario);
        ResponseWrapper<List<Rol>> response = ResponseWrapper.success(roles, "Roles del usuario obtenidos exitosamente");
        return response.toResponseEntity();
    }

    @GetMapping("/pacientes/{idUsuario}")
    public ResponseEntity<?> obtenerPacientePorId(@PathVariable Long idUsuario) {
        Usuario paciente = usuarioService.obtenerPacientePorId(idUsuario);
        ResponseWrapper<Usuario> response = ResponseWrapper.success(paciente, "Paciente encontrado exitosamente");
        return response.toResponseEntity();
    }

    @GetMapping("/pacientes")
    public ResponseEntity<?> listarPacientes() {
        List<Usuario> pacientes = usuarioService.listarPacientes();
        ResponseWrapper<List<Usuario>> response = ResponseWrapper.success(pacientes, "Pacientes obtenidos exitosamente");
        return response.toResponseEntity();
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long idUsuario, @Valid @RequestBody ActualizarUsuarioDTO actualizarUsuarioDTO) {
        Usuario usuario = usuarioService.actualizarUsuario(idUsuario, actualizarUsuarioDTO);
        ResponseWrapper<Usuario> response = ResponseWrapper.success(usuario, "Usuario actualizado exitosamente");
        return response.toResponseEntity();
    }
}
