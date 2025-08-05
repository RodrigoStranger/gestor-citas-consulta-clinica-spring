package com.ulasalle.gestorcitasconsultaclinicaspring.config;

import com.ulasalle.gestorcitasconsultaclinicaspring.model.Rol;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.TipoRol;
import com.ulasalle.gestorcitasconsultaclinicaspring.repository.IRolJPARepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    @Bean
    ApplicationRunner initRoles(IRolJPARepository rolRepository) {
        return args -> {
            if (rolRepository.findByNombre(TipoRol.ADMIN).isEmpty()) {
                Rol rolAdmin = new Rol();
                rolAdmin.setNombre(TipoRol.ADMIN);
                rolAdmin.setDescripcion(TipoRol.ADMIN.getDescripcion());
                rolRepository.save(rolAdmin);
            }
            if (rolRepository.findByNombre(TipoRol.MEDICO).isEmpty()) {
                Rol rolMedico = new Rol();
                rolMedico.setNombre(TipoRol.MEDICO);
                rolMedico.setDescripcion(TipoRol.MEDICO.getDescripcion());
                rolRepository.save(rolMedico);
            }
            if (rolRepository.findByNombre(TipoRol.PACIENTE).isEmpty()) {
                Rol rolPaciente = new Rol();
                rolPaciente.setNombre(TipoRol.PACIENTE);
                rolPaciente.setDescripcion(TipoRol.PACIENTE.getDescripcion());
                rolRepository.save(rolPaciente);
            }
        };
    }
}