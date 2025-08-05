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
            // Verificar y crear rol ADMIN si no existe
            if (!rolRepository.findByNombre(TipoRol.ADMIN).isPresent()) {
                Rol rolAdmin = new Rol();
                rolAdmin.setNombre(TipoRol.ADMIN);
                rolAdmin.setDescripcion(TipoRol.ADMIN.getDescripcion());
                rolRepository.save(rolAdmin);
                System.out.println("Rol ADMIN creado automáticamente");
            }

            // Verificar y crear rol MEDICO si no existe
            if (!rolRepository.findByNombre(TipoRol.MEDICO).isPresent()) {
                Rol rolMedico = new Rol();
                rolMedico.setNombre(TipoRol.MEDICO);
                rolMedico.setDescripcion(TipoRol.MEDICO.getDescripcion());
                rolRepository.save(rolMedico);
                System.out.println("Rol MEDICO creado automáticamente");
            }

            // Verificar y crear rol PACIENTE si no existe
            if (!rolRepository.findByNombre(TipoRol.PACIENTE).isPresent()) {
                Rol rolPaciente = new Rol();
                rolPaciente.setNombre(TipoRol.PACIENTE);
                rolPaciente.setDescripcion(TipoRol.PACIENTE.getDescripcion());
                rolRepository.save(rolPaciente);
                System.out.println("Rol PACIENTE creado automáticamente");
            }

            System.out.println("Inicialización de roles completada");
        };
    }
}
