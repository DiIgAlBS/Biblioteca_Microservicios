package com.Fullstack1.Microservicio2_Usuarios.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Fullstack1.Microservicio2_Usuarios.Model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
    
    Optional<Usuario> findByCorreo(String correo);
}
