package com.ulasalle.gestorcitasconsultaclinicaspring.repository;

import com.ulasalle.gestorcitasconsultaclinicaspring.model.Medico;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.TipoRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMedicoJPARepository extends JpaRepository<Medico, Long> {
    List<Medico> findByUsuario_ActivoAndUsuario_Roles_Nombre(int activo, TipoRol tipoRol);
    Medico findByUsuario_Telefono(String telefono);
    Medico findByUsuario_Correo(String correo);
    Medico findByUsuario_NombreAndUsuario_Apellidos(String nombre, String apellidos);

    @Query("SELECT m FROM Medico m LEFT JOIN FETCH m.horarios WHERE m.id_medico = :idMedico")
    Optional<Medico> findByIdWithHorarios(@Param("idMedico") Long idMedico);
}
