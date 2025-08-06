package com.ulasalle.gestorcitasconsultaclinicaspring.controller;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.RolDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.Usuario;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.IUsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> actualizarClaveUsuario(@PathVariable Long idUsuario, @RequestBody String nuevaClave) {
        usuarioService.actualizarClaveUsuario(idUsuario, nuevaClave);
        ResponseWrapper<Void> response = ResponseWrapper.success(null, "Clave de usuario actualizada exitosamente");
        return response.toResponseEntity();
    }

    @PutMapping("/{idUsuario}/rol")
    public ResponseEntity<?> agregarRolAUsuario(@PathVariable Long idUsuario, @Valid @RequestBody RolDTO rolDTO) {
        Usuario usuario = usuarioService.agregarRolAUsuario(idUsuario, rolDTO.getNombre());
        ResponseWrapper<Usuario> response = ResponseWrapper.success(usuario, "Rol asignado al usuario exitosamente");
        return response.toResponseEntity();
    }

    @DeleteMapping("/{idUsuario}/rol")
    public ResponseEntity<?> quitarRolAUsuario(@PathVariable Long idUsuario, @Valid @RequestBody RolDTO rolDTO) {
        Usuario usuario = usuarioService.quitarRolAUsuario(idUsuario, rolDTO.getNombre());
        ResponseWrapper<Usuario> response = ResponseWrapper.success(usuario, "Rol removido del usuario exitosamente");
        return response.toResponseEntity();
    }
}
