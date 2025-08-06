package com.ulasalle.gestorcitasconsultaclinicaspring.controller;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.MedicoDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.Medico;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.IMedicoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {
    private final IMedicoService medicoService;

    public MedicoController(IMedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @GetMapping("/salud")
    public ResponseEntity<?> estado() {
        ResponseWrapper<String> response = ResponseWrapper.success("Servicio activo", "El servicio de médicos está funcionando correctamente");
        return response.toResponseEntity();
    }

    @PostMapping
    public ResponseEntity<?> crearMedico(@Valid @RequestBody MedicoDTO medicoDTO) {
        Medico medicoCreado = medicoService.crearMedico(medicoDTO);
        ResponseWrapper<Medico> response = ResponseWrapper.success(medicoCreado, "Médico creado exitosamente");
        return response.toResponseEntity();
    }

    @GetMapping("/habilitados")
    public ResponseEntity<?> listarMedicosHabilitados() {
        List<Medico> medicos = medicoService.listarMedicosHabilitados();
        ResponseWrapper<List<Medico>> response = ResponseWrapper.success(medicos, "Médicos habilitados obtenidos exitosamente");
        return response.toResponseEntity();
    }

    @GetMapping("/deshabilitados")
    public ResponseEntity<?> listarMedicosDeshabilitados() {
        List<Medico> medicos = medicoService.listarMedicosDeshabilitados();
        ResponseWrapper<List<Medico>> response = ResponseWrapper.success(medicos, "Médicos deshabilitados obtenidos exitosamente");
        return response.toResponseEntity();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarMedicoPorId(@PathVariable Long id) {
        Medico medico = medicoService.obtenerMedicoPorId(id);
        ResponseWrapper<Medico> response = ResponseWrapper.success(medico, "Médico encontrado exitosamente");
        return response.toResponseEntity();
    }

    @PutMapping("/{id}/estado/{estado}")
    public ResponseEntity<?> cambiarEstadoMedico(@PathVariable Long id, @PathVariable int estado) {
        Medico medico = medicoService.cambiarEstadoMedico(id, estado);
        String mensaje = estado == 0 ? "Médico deshabilitado exitosamente" : "Médico habilitado exitosamente";
        ResponseWrapper<Medico> response = ResponseWrapper.success(medico, mensaje);
        return response.toResponseEntity();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarMedico(@PathVariable Long id, @Valid @RequestBody MedicoDTO medicoDTO) {
        Medico medicoActualizado = medicoService.actualizarMedico(id, medicoDTO);
        ResponseWrapper<Medico> response = ResponseWrapper.success(medicoActualizado, "Médico actualizado exitosamente");
        return response.toResponseEntity();
    }

    @GetMapping("/especialidades")
    public ResponseEntity<?> obtenerTodasLasEspecialidades() {
        Set<String> especialidades = medicoService.obtenerTodasLasEspecialidades();
        ResponseWrapper<Set<String>> response = ResponseWrapper.success(especialidades, "Especialidades obtenidas exitosamente");
        return response.toResponseEntity();
    }
}
