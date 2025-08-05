package com.ulasalle.gestorcitasconsultaclinicaspring.repository;

import com.ulasalle.gestorcitasconsultaclinicaspring.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioJPARepository extends JpaRepository<Usuario, Long> {
    boolean existsByCorreo(String correo);
    boolean existsByDni(String dni);
}
