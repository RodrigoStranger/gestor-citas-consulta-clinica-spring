package com.ulasalle.gestorcitasconsultaclinicaspring.service.util;

import java.time.LocalTime;

public class HorarioClinica {
    public static final LocalTime APERTURA = LocalTime.of(7, 0);
    public static final LocalTime CIERRE = LocalTime.of(20, 0);
    public static final LocalTime INICIO_DESCANSO = LocalTime.of(12, 1);
    public static final LocalTime FIN_DESCANSO = LocalTime.of(12, 59);

    public static boolean esHoraValida(LocalTime hora) {
        boolean dentroHorario = !hora.isBefore(APERTURA) && !hora.isAfter(CIERRE);
        boolean fueraDescanso = hora.isBefore(INICIO_DESCANSO) || hora.isAfter(FIN_DESCANSO);
        return dentroHorario && fueraDescanso;
    }
}