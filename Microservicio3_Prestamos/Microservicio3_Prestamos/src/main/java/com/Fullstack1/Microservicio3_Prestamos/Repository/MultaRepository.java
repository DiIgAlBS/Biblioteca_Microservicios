package com.Fullstack1.Microservicio3_Prestamos.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Fullstack1.Microservicio3_Prestamos.Model.Multa;

@Repository
public interface MultaRepository extends JpaRepository<Multa, Integer> { 
    @Transactional(readOnly = true)
    List<Multa> findByUsuarioId(Integer usuarioId); 
}
