package com.Fullstack1.Microservicio1_Libros.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Fullstack1.Microservicio1_Libros.Model.Libro;

import feign.Param;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Integer> {
    
    List<Libro> findByAutorId(Integer autorId);
    List<Libro> findByEditorialId(Integer editorialId);
    List<Libro> findByIdiomaId(Integer idiomaId);
    List<Libro> findByCategoriaId(Integer categoriaId);
    
    @Query("SELECT l FROM Libro l WHERE LOWER(l.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Libro> buscarPorNombre(@Param("nombre") String nombre);
}
