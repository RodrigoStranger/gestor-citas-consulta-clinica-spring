package com.ulasalle.gestorcitasconsultaclinicaspring.controller;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.EspecialidadDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.Especialidad;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.IEspecialidadService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
@CrossOrigin(origins = "*")
public class EspecialidadController {

    private final IEspecialidadService especialidadService;

    public EspecialidadController(IEspecialidadService especialidadService) {
        this.especialidadService = especialidadService;
    }

    @PostMapping
    public ResponseEntity<?> crearEspecialidad(@Valid @RequestBody EspecialidadDTO especialidadDTO) {
        Especialidad especialidad = especialidadService.crearEspecialidad(especialidadDTO);
        ResponseWrapper<Especialidad> response = ResponseWrapper.success(especialidad, "Especialidad creada exitosamente");
        return response.toResponseEntity();
    }

    @GetMapping
    public ResponseEntity<?> listarEspecialidades() {
        List<Especialidad> especialidades = especialidadService.listarEspecialidades();
        ResponseWrapper<List<Especialidad>> response = ResponseWrapper.success(especialidades, "Lista de especialidades obtenida exitosamente");
        return response.toResponseEntity();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarEspecialidadPorId(@PathVariable Long id) {
        Especialidad especialidad = especialidadService.buscarPorId(id);
        ResponseWrapper<Especialidad> response = ResponseWrapper.success(especialidad, "Especialidad encontrada exitosamente");
        return response.toResponseEntity();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEspecialidad(@PathVariable Long id, @Valid @RequestBody EspecialidadDTO especialidadDTO) {
        Especialidad especialidad = especialidadService.actualizarEspecialidad(id, especialidadDTO);
        ResponseWrapper<Especialidad> response = ResponseWrapper.success(especialidad, "Especialidad actualizada exitosamente");
        return response.toResponseEntity();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEspecialidad(@PathVariable Long id) {
        especialidadService.eliminarEspecialidad(id);
        ResponseWrapper<Void> response = ResponseWrapper.success(null, "Especialidad eliminada exitosamente");
        return response.toResponseEntity();
    }
}
