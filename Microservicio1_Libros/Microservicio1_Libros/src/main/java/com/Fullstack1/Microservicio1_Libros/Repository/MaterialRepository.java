package com.Fullstack1.Microservicio1_Libros.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Fullstack1.Microservicio1_Libros.Model.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    
    List<Material> findByLibroId(Integer libroId);
    List<Material> findByEstado(String estado);
    List<Material> findByTipoMaterial(String tipoMaterial);
}
