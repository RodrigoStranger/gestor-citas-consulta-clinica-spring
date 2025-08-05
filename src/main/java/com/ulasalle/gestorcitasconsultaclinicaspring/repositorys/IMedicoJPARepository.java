package com.ulasalle.gestorcitasconsultaclinicaspring.repositorys;

import com.ulasalle.gestorcitasconsultaclinicaspring.models.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IMedicoJPARepository extends JpaRepository<Medico, Long> {
    boolean existsByNombre(String nombre);
    boolean existsByDni(String dni);
    boolean existsByCorreo(String correo);
}
