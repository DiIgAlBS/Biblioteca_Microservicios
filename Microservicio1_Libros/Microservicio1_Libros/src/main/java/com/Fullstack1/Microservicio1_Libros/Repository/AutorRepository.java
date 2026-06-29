package com.Fullstack1.Microservicio1_Libros.Repository;

import com.Fullstack1.Microservicio1_Libros.Model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Integer> {
    Optional<Autor> findByRut(String rut);

    Optional<Autor> findByCorreo(String correo);

    @Query("SELECT a FROM Autor a WHERE LOWER(a.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Autor> buscarPorNombre(@Param("nombre") String nombre);

    List<Autor> findByNacionalidad(String nacionalidad);

    boolean existsByRut(String rut);

    boolean existsByCorreo(String correo);
}
