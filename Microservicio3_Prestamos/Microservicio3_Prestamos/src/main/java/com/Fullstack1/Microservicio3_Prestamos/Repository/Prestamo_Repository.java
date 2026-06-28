package com.Fullstack1.Microservicio3_Prestamos.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Fullstack1.Microservicio3_Prestamos.Model.Prestamo;

@Repository
public interface Prestamo_Repository extends JpaRepository<Prestamo, Integer>{ // Al heredar de JpaRepository, obtiene automáticamente los comandos básicos (guardar, buscar, eliminar)
}
