package com.ulasalle.gestorcitasconsultaclinicaspring.repository;

import com.ulasalle.gestorcitasconsultaclinicaspring.model.Medico;
import com.ulasalle.gestorcitasconsultaclinicaspring.model.TipoRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMedicoJPARepository extends JpaRepository<Medico, Long> {
    List<Medico> findByUsuario_ActivoAndUsuario_Roles_Nombre(int activo, TipoRol tipoRol);
    Medico findByUsuario_Telefono(String telefono);
    Medico findByUsuario_Correo(String correo);
    Medico findByUsuario_NombreAndUsuario_Apellidos(String nombre, String apellidos);
}
