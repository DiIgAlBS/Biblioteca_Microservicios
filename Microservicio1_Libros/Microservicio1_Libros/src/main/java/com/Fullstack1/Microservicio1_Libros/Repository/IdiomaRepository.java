package com.Fullstack1.Microservicio1_Libros.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Fullstack1.Microservicio1_Libros.Model.Idioma;

@Repository
public interface IdiomaRepository extends JpaRepository<Idioma, Integer> {
    
    Optional<Idioma> findByNombreIdioma(String nombreIdioma);
    boolean existsByNombreIdioma(String nombreIdioma);
}
