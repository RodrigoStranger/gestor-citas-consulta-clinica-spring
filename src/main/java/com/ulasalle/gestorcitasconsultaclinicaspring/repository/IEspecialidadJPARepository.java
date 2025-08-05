package com.ulasalle.gestorcitasconsultaclinicaspring.repository;

import com.ulasalle.gestorcitasconsultaclinicaspring.model.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEspecialidadJPARepository extends JpaRepository<Especialidad, Long> {
    // Solo mantener la funcionalidad básica de JpaRepository
    // Spring Data JPA provee automáticamente: findById, findAll, save, delete, etc.
}
