package com.ulasalle.gestorcitasconsultaclinicaspring.service.impl;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.dto.HorarioDTO;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.Horario;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.TipoDiaSemana;
import com.ulasalle.gestorcitasconsultaclinicaspring.repository.IHorarioJPARepository;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.IHorarioService;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.exception.BusinessException;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.exception.ErrorCodeEnum;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.util.HorarioClinica;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class IHorarioServiceImpl implements IHorarioService {
    private final IHorarioJPARepository horarioRepository;

    public IHorarioServiceImpl(IHorarioJPARepository horarioRepository) {
        this.horarioRepository = horarioRepository;
    }

    @Override
    public Horario crearHorario(HorarioDTO dto) {
        TipoDiaSemana tipoDiaSemana;
        try {
            tipoDiaSemana = TipoDiaSemana.valueOf(dto.getTipoDiaSemana());
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCodeEnum.HORARIO_INVALIDO);
        }
        LocalTime horaInicio = LocalTime.parse(dto.getHoraInicio());
        LocalTime horaFin = LocalTime.parse(dto.getHoraFin());
        if (!HorarioClinica.esHoraValida(horaInicio) || !HorarioClinica.esHoraValida(horaFin)) {
            throw new BusinessException(ErrorCodeEnum.HORARIO_INVALIDO);
        }
        List<Horario> duplicados = horarioRepository.findByTipoDiaSemanaAndHoraInicioAndHoraFin(tipoDiaSemana, horaInicio, horaFin);
        if (!duplicados.isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.HORARIO_DUPLICADO);
        }
        Horario horario = new Horario();
        horario.setTipoDiaSemana(tipoDiaSemana);
        horario.setHoraInicio(horaInicio);
        horario.setHoraFin(horaFin);
        return horarioRepository.save(horario);
    }
}

