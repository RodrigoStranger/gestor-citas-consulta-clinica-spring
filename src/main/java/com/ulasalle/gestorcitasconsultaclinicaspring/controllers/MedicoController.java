package com.ulasalle.gestorcitasconsultaclinicaspring.controllers;

import com.ulasalle.gestorcitasconsultaclinicaspring.controllers.dtos.MedicoDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.models.Medico;
import com.ulasalle.gestorcitasconsultaclinicaspring.services.IMedicoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> crearMedico(@Valid @RequestBody MedicoDTO medicoDTO) {
        Medico medicoCreado = medicoService.crearMedico(medicoDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Médico creado satisfactoriamente");
        response.put("status", "201");
        response.put("data", medicoCreado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
