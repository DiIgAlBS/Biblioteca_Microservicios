package com.Fullstack1.Microservicio3_Prestamos.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Fullstack1.Microservicio3_Prestamos.DTO.PrestamoDTO;
import com.Fullstack1.Microservicio3_Prestamos.DTO.PrestamoRequestDTO;
import com.Fullstack1.Microservicio3_Prestamos.Model.Estado_Prestamo;
import com.Fullstack1.Microservicio3_Prestamos.Model.Prestamo;
import com.Fullstack1.Microservicio3_Prestamos.Repository.Multa_Repository;
import com.Fullstack1.Microservicio3_Prestamos.Repository.Prestamo_Repository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class Prestamo_Service {
    private final Prestamo_Repository prestamoRepository;
    private final Multa_Repository multaRepository;
    
    public List<PrestamoDTO> obtenerTodos() {
        log.info("Service: Obteniendo lista completa de préstamos");
        List<PrestamoDTO> prestamos = prestamoRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        log.debug("Se obtuvieron {} préstamos", prestamos.size());
        return prestamos;
    }

    public PrestamoDTO buscarPorId(Integer id) {
        log.info("Service: Buscando préstamo con ID: {}", id);
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Préstamo no encontrado con ID: {}", id);
                    return new RuntimeException("Préstamo no encontrado con ID: " + id);
                });
        log.debug("Préstamo encontrado: ID {}", id);
        return convertirADTO(prestamo);
    }

    public PrestamoDTO crearPrestamo(PrestamoRequestDTO request) {
        log.info("Service: Iniciando validación y registro de préstamo de libro ID: {}", request.getLibroId());
        
        if (request.getUsuarioId() == null) {
            throw new RuntimeException("El ID del usuario es obligatorio");
        }
        if (request.getLibroId() == null) {
            throw new RuntimeException("El ID del libro es obligatorio");
        }

        Prestamo prestamo = Prestamo.builder()
            .usuarioId(request.getUsuarioId())
            .libroId(request.getLibroId())
            .fechaPrestamo(LocalDate.now())
            .fechaDevolucion(request.getFechaDevolucion())
            .estado(Estado_Prestamo.ACTIVO)
            .build();
            
        Prestamo guardado = prestamoRepository.save(prestamo);
        log.info("Préstamo registrado exitosamente con ID: {}", guardado.getId());
        return convertirADTO(guardado);
    }

    public PrestamoDTO actualizar(Integer id, PrestamoRequestDTO request) {
        log.info("Service: Actualizando préstamo con ID: {}", id);
        
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Préstamo no encontrado con ID: {}", id);
                    return new RuntimeException("Préstamo no encontrado con ID: " + id);
                });

        if (request.getFechaDevolucion() != null) {
            prestamo.setFechaDevolucion(request.getFechaDevolucion());
        }

        Prestamo actualizado = prestamoRepository.save(prestamo);
        log.info("Préstamo actualizado exitosamente: ID {}", id);
        return convertirADTO(actualizado);
    }

    public void eliminar(Integer id) {
        log.info("Service: Eliminando préstamo con ID: {}", id);
        
        if (!prestamoRepository.existsById(id)) {
            log.error("Préstamo no encontrado con ID: {}", id);
            throw new RuntimeException("Préstamo no encontrado con ID: " + id);
        }
        
        prestamoRepository.deleteById(id);
        log.info("Préstamo eliminado exitosamente: ID {}", id);
    }

    private PrestamoDTO convertirADTO(Prestamo p) {
        return PrestamoDTO.builder()
            .id(p.getId())
            .usuarioId(p.getUsuarioId())
            .libroId(p.getLibroId())
            .fechaPrestamo(p.getFechaPrestamo())
            .fechaDevolucion(p.getFechaDevolucion())
            .estado(p.getEstado())
            .build();
    }
}
