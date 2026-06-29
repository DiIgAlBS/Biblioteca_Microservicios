package com.Fullstack1.Microservicio2_Usuarios.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Fullstack1.Microservicio2_Usuarios.Model.Notificacion;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {
    List<Notificacion> findByUsuarioId(Integer usuarioId); 

    List<Notificacion> findByUsuarioIdAndLeidaFalse(Integer usuarioId);
}
