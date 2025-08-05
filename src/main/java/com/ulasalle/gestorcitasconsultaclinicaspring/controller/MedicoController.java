package com.ulasalle.gestorcitasconsultaclinicaspring.controller;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.MedicoDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.Medico;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.IMedicoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    private final IMedicoService medicoService;
    public MedicoController(IMedicoService medicoService) {
        this.medicoService = medicoService;
    }
    @GetMapping("/salud")
    public ResponseEntity<Map<String, Object>> estado() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "El servicio de médicos está activo");
        response.put("status", "200");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PostMapping
    public ResponseEntity<Map<String, Object>> crearMedico(@Valid @RequestBody MedicoDTO medicoDTO) {
        Medico medicoCreado = medicoService.crearMedico(medicoDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Médico creado satisfactoriamente");
        response.put("data", medicoCreado);
        response.put("status", "201");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/habilitados")
    public ResponseEntity<Map<String, Object>> listarMedicosHabilitados() {
        List<Medico> medicos = medicoService.listarMedicosHabilitados();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Médicos habilitados obtenidos satisfactoriamente");
        response.put("data", medicos);
        response.put("total", medicos.size());
        response.put("status", "200");
        return ResponseEntity.ok(response);
    }
    @GetMapping("/deshabilitados")
    public ResponseEntity<Map<String, Object>> listarMedicosDeshabilitados() {
        List<Medico> medicos = medicoService.listarMedicosDeshabilitados();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Médicos deshabilitados obtenidos satisfactoriamente");
        response.put("data", medicos);
        response.put("total", medicos.size());
        response.put("status", "200");
        return ResponseEntity.ok(response);
    }
}
