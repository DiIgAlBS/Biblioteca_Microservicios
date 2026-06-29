package com.Fullstack1.Microservicio2_Usuarios.Service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Fullstack1.Microservicio2_Usuarios.DTO.UsuarioDTO;
import com.Fullstack1.Microservicio2_Usuarios.DTO.UsuarioRequestDTO;
import com.Fullstack1.Microservicio2_Usuarios.Model.Usuario;
import com.Fullstack1.Microservicio2_Usuarios.Repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<UsuarioDTO> obtenerTodos() {
        log.info("Service: Consultando lista completa de usuarios");
        return usuarioRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorId(Integer id) {
        log.info("Service: Buscando usuario con ID: {}", id);
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> {
            log.error("Usuario no encontrado con ID: {}", id);
            return new RuntimeException("Usuario no encontrado con ID: " + id);
        });
        return convertirADTO(usuario);
    }

    public UsuarioDTO guardar(UsuarioRequestDTO request) {
    log.info("Service: Guardando nuevo usuario: {}", request.getNombre());        
    Usuario nuevoUsuario = Usuario.builder()
        .nombre(request.getNombre())
        .correo(request.getCorreo())
        .numero(request.getNumero())
        .build();          
    Usuario guardado = usuarioRepository.save(nuevoUsuario);
    log.info("Usuario creado exitosamente con ID: {}", guardado.getId());
    return convertirADTO(guardado);
}

    public UsuarioDTO actualizar(Integer id, UsuarioRequestDTO request) {
        log.info("Service: Actualizando datos del usuario ID: {}", id);
        Usuario user = usuarioRepository.findById(id).orElseThrow(() -> {
            log.error("Error al actualizar. Usuario no existe con ID: {}", id);
            return new RuntimeException("¡El usuario no existe en los registros!");
        });
        if (request.getNombre() != null && !request.getNombre().trim().isEmpty()) {
            user.setNombre(request.getNombre());
        }
        if (request.getCorreo() != null && !request.getCorreo().trim().isEmpty()) {
            user.setCorreo(request.getCorreo());
        }
        if (request.getNumero() != null && !request.getNumero().trim().isEmpty()) {
            user.setNumero(request.getNumero());
        }
        Usuario actualizado = usuarioRepository.save(user);
        log.info("Usuario ID {} actualizado con éxito", id);
        return convertirADTO(actualizado);
    }

    public void eliminar(Integer id) {
        log.info("Service: Eliminando usuario con ID: {}", id);
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> {
            log.error("Error al eliminar. ID {} no existe.", id);
            return new RuntimeException("¡Imposible eliminar! ID " + id + " no existe.");
        });      
        usuarioRepository.delete(usuario);
        log.info("Usuario con ID {} eliminado exitosamente", id);
    }

    private UsuarioDTO convertirADTO(Usuario usuario) {
    return UsuarioDTO.builder()
        .id(usuario.getId())
        .nombre(usuario.getNombre())
        .correo(usuario.getCorreo())
        .numero(usuario.getNumero())
        .estadoNotificaciones("ACTIVO") 
        .librosIds(List.of())           
        .nombresLibros(List.of())       
        .build();
    }
}
