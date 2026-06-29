package com.Fullstack1.Microservicio1_Libros.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Fullstack1.Microservicio1_Libros.Model.Editorial;

@Repository
public interface EditorialRepository extends JpaRepository<Editorial, Integer> {
    
    Optional<Editorial> findByNombre(String nombre);
    Optional<Editorial> findByCorreoContacto(String correoContacto);
    boolean existsByNombre(String nombre);
    boolean existsByCorreoContacto(String correoContacto);

}
