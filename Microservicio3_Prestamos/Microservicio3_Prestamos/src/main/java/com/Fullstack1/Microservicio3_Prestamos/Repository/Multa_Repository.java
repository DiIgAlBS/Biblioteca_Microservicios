package com.Fullstack1.Microservicio3_Prestamos.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Fullstack1.Microservicio3_Prestamos.Model.Multa;

@Repository
public interface Multa_Repository extends JpaRepository<Multa, Integer> { // Es una "interface", no una "class". Hereda todo el poder de JpaRepository.
    List<Multa> findByUsuarioId(Integer usuarioId); // Retorna todas las multas que le pertenezcan a un usuario específico.
}
