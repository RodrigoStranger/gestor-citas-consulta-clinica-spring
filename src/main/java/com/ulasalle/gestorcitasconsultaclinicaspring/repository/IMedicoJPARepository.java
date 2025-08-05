package com.ulasalle.gestorcitasconsultaclinicaspring.repository;

import com.ulasalle.gestorcitasconsultaclinicaspring.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMedicoJPARepository extends JpaRepository<Medico, Long> {

    @Query("SELECT m FROM Medico m WHERE m.usuario.activo = :activo")
    List<Medico> findByUsuarioActivo(int activo);

    // Métodos más específicos para mejor legibilidad
    @Query("SELECT m FROM Medico m WHERE m.usuario.activo = 1")
    List<Medico> findMedicosHabilitados();

    @Query("SELECT m FROM Medico m WHERE m.usuario.activo = 0")
    List<Medico> findMedicosDeshabilitados();
}
