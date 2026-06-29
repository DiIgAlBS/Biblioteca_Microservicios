package com.Fullstack1.Microservicio2_Usuarios.Service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Fullstack1.Microservicio2_Usuarios.DTO.NotificacionDTO;
import com.Fullstack1.Microservicio2_Usuarios.DTO.NotificacionRequestDTO;
import com.Fullstack1.Microservicio2_Usuarios.Model.Notificacion;
import com.Fullstack1.Microservicio2_Usuarios.Repository.NotificacionRepository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;

    @Transactional(readOnly = true)
    public List<NotificacionDTO> obtenerTodas() {
        log.info("Service: Obteniendo lista completa de notificaciones");
        return notificacionRepository.findAll().stream()
            .map(this::convertirADTO)
            .toList();
    }

    @Transactional(readOnly = true)
    public NotificacionDTO buscarPorId(Integer id) {
        log.info("Service: Buscando notificación con ID: {}", id);
        Notificacion notificacion = notificacionRepository.findById(id).orElseThrow(() -> {
            log.error("Notificación no encontrada con ID: {}", id);
            return new RuntimeException("Notificación no encontrada con ID: " + id);
        });
        return convertirADTO(notificacion);
    }

    @Transactional(readOnly = true)
    public List<NotificacionDTO> obtenerPorUsuarioId(Integer usuarioId) {
        log.info("Service: Obteniendo notificaciones del usuario ID: {}", usuarioId);
        return notificacionRepository.findByUsuarioId(usuarioId).stream()
            .map(this::convertirADTO)
            .toList();
    }

    public NotificacionDTO crearNotificacion(NotificacionRequestDTO request) {
        log.info("Service: Creando nueva notificación para usuario ID: {}", request.getUsuarioId());
        Notificacion notificacion = Notificacion.builder()
            .usuarioId(request.getUsuarioId())
            .mensaje(request.getMensaje())
            .fechaEnvio(LocalDate.now())
            .tipo(request.getTipo())
            .leida(false)
            .build();
        Notificacion guardada = notificacionRepository.save(notificacion);
        log.info("Notificación creada exitosamente con ID: {}", guardada.getId());
        return convertirADTO(guardada);
    }

    public NotificacionDTO actualizar(Integer id, NotificacionRequestDTO request) {
        log.info("Service: Actualizando notificación con ID: {}", id);
        Notificacion notificacion = notificacionRepository.findById(id).orElseThrow(() -> {
            log.error("Notificación no encontrada con ID: {}", id);
            return new RuntimeException("Notificación no encontrada con ID: " + id);
        });
        if (request.getMensaje() != null && !request.getMensaje().trim().isEmpty()) {
            notificacion.setMensaje(request.getMensaje());
        }
        if (request.getTipo() != null) {
            notificacion.setTipo(request.getTipo());
        }
        Notificacion actualizada = notificacionRepository.save(notificacion);
        log.info("Notificación actualizada exitosamente: ID {}", id);
        return convertirADTO(actualizada);
    }

    public void eliminar(Integer id) {
        log.info("Service: Eliminando notificación con ID: {}", id);
        if (!notificacionRepository.existsById(id)) {
            log.error("Notificación no encontrada con ID: {}", id);
            throw new RuntimeException("Notificación no encontrada con ID: " + id);
        }
        notificacionRepository.deleteById(id);
        log.info("Notificación eliminada exitosamente: ID {}", id);
    }

    private NotificacionDTO convertirADTO(Notificacion n) {
        return NotificacionDTO.builder()
                .id(n.getId())
                .usuarioId(n.getUsuarioId())
                .mensaje(n.getMensaje())
                .fechaEnvio(n.getFechaEnvio())
                .leida(n.getLeida())
                .tipo(n.getTipo())
                .build();
    }
}
