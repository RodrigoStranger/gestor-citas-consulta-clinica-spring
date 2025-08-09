package com.ulasalle.gestorcitasconsultaclinicaspring.controller;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.CrearCitaMedicaDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.CitaMedica;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.ICitaMedicaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/citas")
public class CitaMedicaController {

    private final ICitaMedicaService citaMedicaService;

    public CitaMedicaController(ICitaMedicaService citaMedicaService) {
        this.citaMedicaService = citaMedicaService;
    }

    @PostMapping
    public ResponseEntity<?> crearCita(@Valid @RequestBody CrearCitaMedicaDTO crearCitaMedicaDTO) {
        CitaMedica nuevaCita = citaMedicaService.crearCitaMedica(crearCitaMedicaDTO);
        var response = ResponseWrapper.success(nuevaCita, "Cita médica creada exitosamente");
        return response.toResponseEntity();
    }

    @GetMapping
    public ResponseEntity<?> obtenerTodasLasCitas() {
        var citas = citaMedicaService.obtenerTodasLasCitas();
        var response = ResponseWrapper.success(citas, "Citas obtenidos exitosamente");
        return response.toResponseEntity();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCitaPorId(@PathVariable Long id) {
        var cita = citaMedicaService.obtenerCitaPorId(id);
        var response = ResponseWrapper.success(cita, "Cita obtenida exitosamente");
        return response.toResponseEntity();
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> obtenerCitasPorIdUsuario(@PathVariable Long idUsuario) {
        var citas = citaMedicaService.obtenerCitasPorIdUsuario(idUsuario);
        var response = ResponseWrapper.success(citas, "Citas del usuario obtenidas exitosamente");
        return response.toResponseEntity();
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelarCita(@PathVariable Long id) {
        var citaCancelada = citaMedicaService.cancelarCita(id);
        var response = ResponseWrapper.success(citaCancelada, "Cita médica cancelada exitosamente");
        return response.toResponseEntity();
    }
}
