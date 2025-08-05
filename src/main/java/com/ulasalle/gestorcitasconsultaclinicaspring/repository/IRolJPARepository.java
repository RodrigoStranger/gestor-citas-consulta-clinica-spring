package com.ulasalle.gestorcitasconsultaclinicaspring.repository;

import com.ulasalle.gestorcitasconsultaclinicaspring.model.Rol;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.TipoRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRolJPARepository extends JpaRepository<Rol, Long> {

    Optional<Rol> findByNombre(TipoRol nombre);
}
