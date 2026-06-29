package com.Fullstack1.Microservicio1_Libros.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Fullstack1.Microservicio1_Libros.Model.Inventario;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Integer> {
}
