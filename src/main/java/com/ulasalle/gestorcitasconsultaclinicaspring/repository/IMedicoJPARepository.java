package com.ulasalle.gestorcitasconsultaclinicaspring.repository;

import com.ulasalle.gestorcitasconsultaclinicaspring.model.Medico;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.TipoRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMedicoJPARepository extends JpaRepository<Medico, Long> {
    List<Medico> findByUsuarioActivo(int activo);

    @Query("SELECT m FROM Medico m JOIN m.usuario u JOIN u.roles r WHERE u.activo = 1 AND r.nombre = :tipoRol")
    List<Medico> findMedicosHabilitados(TipoRol tipoRol);

    @Query("SELECT m FROM Medico m JOIN m.usuario u JOIN u.roles r WHERE u.activo = 0 AND r.nombre = :tipoRol")
    List<Medico> findMedicosDeshabilitados(TipoRol tipoRol);
}
