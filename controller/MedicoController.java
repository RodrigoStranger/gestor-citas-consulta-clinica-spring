// ...existing code...
import com.ulasalle.gestorcitasconsultaclinicaspring.model.Horario;
// ...existing code...

    @PostMapping("/asignar-horario")
    public ResponseEntity<?> asignarHorarioAMedico(@RequestBody AsignarHorarioMedicoDTO dto) {
        Horario horario = medicoService.asignarHorarioAMedicoYRetornarHorario(dto);
        var response = ResponseWrapper.success(horario, "Horario asignado exitosamente al médico");
        return response.toResponseEntity();
    }
// ...existing code...
