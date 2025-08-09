package com.ulasalle.gestorcitasconsultaclinicaspring.service.impl;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.CrearCitaMedicaDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.CitaMedica;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.EstadoCita;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.Medico;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.TipoDiaSemana;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.TipoRol;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.Usuario;
import com.ulasalle.gestorcitasconsultaclinicaspring.repository.ICitaMedicaJPARepository;
import com.ulasalle.gestorcitasconsultaclinicaspring.repository.IMedicoJPARepository;
import com.ulasalle.gestorcitasconsultaclinicaspring.repository.IUsuarioJPARepository;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.ICitaMedicaService;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.exception.BusinessException;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.exception.ErrorCodeEnum;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;

import static com.ulasalle.gestorcitasconsultaclinicaspring.service.exception.ErrorCodeEnum.*;

@Service
public class ICitaMedicaServiceImpl implements ICitaMedicaService {

    private final IMedicoJPARepository medicoRepository;
    private final IUsuarioJPARepository usuarioRepository;
    private final ICitaMedicaJPARepository citaMedicaRepository;

    public ICitaMedicaServiceImpl(IMedicoJPARepository medicoRepository, IUsuarioJPARepository usuarioRepository, ICitaMedicaJPARepository citaMedicaRepository) {
        this.medicoRepository = medicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.citaMedicaRepository = citaMedicaRepository;
    }

    @Override
    public CitaMedica crearCitaMedica(CrearCitaMedicaDTO crearCitaMedicaDTO) {
        var medico = obtenerMedico(crearCitaMedicaDTO.getIdMedico());
        validarRolMedico(medico);

        var usuario = obtenerUsuario(crearCitaMedicaDTO.getIdUsuario());
        validarRolUsuario(usuario);
        validarHorariosMedico(medico, crearCitaMedicaDTO);
        validarSolapamientoCita(medico, crearCitaMedicaDTO);
        CitaMedica nuevaCita = guardarCitaMedica(medico, usuario, crearCitaMedicaDTO);
        nuevaCita.setEstado(EstadoCita.PROCESO);

        return nuevaCita;
    }

    private Medico obtenerMedico(Long idMedico) {
        return medicoRepository.findByIdWithHorarios(idMedico)
                .orElseThrow(() -> new BusinessException(MEDICO_NO_ENCONTRADO));
    }

    private void validarRolMedico(Medico medico) {
        if (!medico.getUsuario().getRolPorDefecto().equals(TipoRol.MEDICO)) {
            throw new BusinessException(MEDICO_NO_ES_VALIDO);
        }
    }

    private Usuario obtenerUsuario(Long idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new BusinessException(USUARIO_NO_ENCONTRADO));
    }

    private void validarRolUsuario(Usuario usuario) {
        if (!usuario.getRolPorDefecto().equals(TipoRol.PACIENTE)) {
            throw new BusinessException(USUARIO_NO_ES_PACIENTE);
        }
    }

    private TipoDiaSemana obtenerTipoDiaSemana(DayOfWeek diaSemana) {
        return switch (diaSemana) {
            case MONDAY -> TipoDiaSemana.LUNES;
            case TUESDAY -> TipoDiaSemana.MARTES;
            case WEDNESDAY -> TipoDiaSemana.MIERCOLES;
            case THURSDAY -> TipoDiaSemana.JUEVES;
            case FRIDAY -> TipoDiaSemana.VIERNES;
            case SATURDAY -> TipoDiaSemana.SABADO;
            case SUNDAY -> TipoDiaSemana.DOMINGO;
        };
    }

    private void validarHorariosMedico(Medico medico, CrearCitaMedicaDTO crearCitaMedicaDTO) {
        if (medico.getHorarios() == null || medico.getHorarios().isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.MEDICO_NO_TIENE_HORARIO);
        }
        DayOfWeek diaSemana = crearCitaMedicaDTO.getFechaCita().getDayOfWeek();
        TipoDiaSemana tipoDiaSemana = obtenerTipoDiaSemana(diaSemana);
        boolean horarioValido = medico.getHorarios().stream()
                .anyMatch(horario -> horario.getTipoDiaSemana().equals(tipoDiaSemana) &&
                        !crearCitaMedicaDTO.getHoraInicio().isBefore(horario.getHoraInicio()) &&
                        !crearCitaMedicaDTO.getHoraFin().isAfter(horario.getHoraFin()));

        if (!horarioValido) {
            throw new BusinessException(ErrorCodeEnum.MEDICO_NO_TIENE_HORARIO);
        }
    }

    private void validarSolapamientoCita(Medico medico, CrearCitaMedicaDTO crearCitaMedicaDTO) {
        boolean solapamiento = citaMedicaRepository.existsByMedicoAndFechaCitaAndHoraInicioBetween(
                medico, crearCitaMedicaDTO.getFechaCita(), crearCitaMedicaDTO.getHoraInicio(), crearCitaMedicaDTO.getHoraFin());

        if (solapamiento) {
            throw new BusinessException(CITA_SOLAPADA);
        }
    }

    private CitaMedica guardarCitaMedica(Medico medico, Usuario usuario, CrearCitaMedicaDTO crearCitaMedicaDTO) {
        CitaMedica nuevaCita = new CitaMedica();
        nuevaCita.setMedico(medico);
        nuevaCita.setUsuario(usuario);
        nuevaCita.setFechaCita(crearCitaMedicaDTO.getFechaCita());
        nuevaCita.setHoraInicio(crearCitaMedicaDTO.getHoraInicio());
        nuevaCita.setHoraFin(crearCitaMedicaDTO.getHoraFin());
        nuevaCita.setEstado(EstadoCita.PROCESO);

        return citaMedicaRepository.save(nuevaCita);
    }

    @Override
    public List<CitaMedica> obtenerTodasLasCitas() {
        return citaMedicaRepository.findAll();
    }

    @Override
    public CitaMedica obtenerCitaPorId(Long id) {
        return citaMedicaRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCodeEnum.CITA_NO_ENCONTRADA));
    }

    @Override
    public List<CitaMedica> obtenerCitasPorIdUsuario(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new BusinessException(ErrorCodeEnum.USUARIO_NO_ENCONTRADO));

        if (!usuario.getRolPorDefecto().equals(TipoRol.PACIENTE)) {
            throw new BusinessException(ErrorCodeEnum.USUARIO_NO_ES_PACIENTE);
        }

        return citaMedicaRepository.findByUsuario(usuario);
    }

    @Override
    public void cancelarCita(Long id) {
        CitaMedica cita = citaMedicaRepository.findById(id)
            .orElseThrow(() -> new BusinessException(ErrorCodeEnum.CITA_NO_ENCONTRADA));
        cita.setEstado(EstadoCita.CANCELADO);
        citaMedicaRepository.save(cita);
    }
}
