package com.ulasalle.gestorcitasconsultaclinicaspring.repositorys;

import com.ulasalle.gestorcitasconsultaclinicaspring.models.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMedicoJPARepository extends JpaRepository<Medico, Long> {
    boolean existsByDni(String dni);
    boolean existsByCorreo(String correo);
    List<Medico> findByActivo(int activo);
}
