package com.Fullstack1.Microservicio3_Prestamos.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Fullstack1.Microservicio3_Prestamos.Model.EstadoPrestamo;
import com.Fullstack1.Microservicio3_Prestamos.Model.Prestamo;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Integer>{ 
    @Transactional(readOnly = true)
    List<Prestamo> findByUsuarioId(Integer usuarioId);

    @Transactional(readOnly = true)
    List<Prestamo> findByUsuarioIdAndEstado(Integer usuarioId, EstadoPrestamo estado);
}
