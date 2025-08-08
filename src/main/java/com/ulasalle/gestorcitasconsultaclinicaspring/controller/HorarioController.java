package com.ulasalle.gestorcitasconsultaclinicaspring.controller;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.HorarioDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.Horario;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.IHorarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/horarios")
public class HorarioController {
    private final IHorarioService horarioService;

    public HorarioController(IHorarioService horarioService) {
        this.horarioService = horarioService;
    }

    @PostMapping
    public ResponseEntity<?> crearHorario(@Valid @RequestBody HorarioDTO dto) {
        Horario horario = horarioService.crearHorario(dto);
        var response = ResponseWrapper.success(horario, "Horario creado exitosamente");
        return response.toResponseEntity();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarHorario(@PathVariable Long id, @Valid @RequestBody HorarioDTO dto) {
        Horario horarioActualizado = horarioService.actualizarHorario(id, dto);
        var response = ResponseWrapper.success(horarioActualizado, "Horario actualizado exitosamente");
        return response.toResponseEntity();
    }

    @GetMapping
    public ResponseEntity<?> listarHorarios() {
        var horarios = horarioService.listarHorarios();
        var response = ResponseWrapper.success(horarios, "Horarios obtenidos exitosamente");
        return response.toResponseEntity();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerHorarioPorId(@PathVariable Long id) {
        Horario horario = horarioService.obtenerHorarioPorId(id);
        var response = ResponseWrapper.success(horario, "Horario obtenido exitosamente");
        return response.toResponseEntity();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarHorarioPorId(@PathVariable Long id) {
        horarioService.eliminarHorarioPorId(id);
        var response = ResponseWrapper.success(null, "Horario eliminado exitosamente");
        return response.toResponseEntity();
    }
}
