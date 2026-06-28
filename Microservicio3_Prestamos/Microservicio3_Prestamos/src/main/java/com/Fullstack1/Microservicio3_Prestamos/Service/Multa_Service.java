package com.Fullstack1.Microservicio3_Prestamos.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Fullstack1.Microservicio3_Prestamos.DTO.MultaDTO;
import com.Fullstack1.Microservicio3_Prestamos.DTO.MultaRequestDTO;
import com.Fullstack1.Microservicio3_Prestamos.Model.Estado_Multa;
import com.Fullstack1.Microservicio3_Prestamos.Model.Multa;
import com.Fullstack1.Microservicio3_Prestamos.Repository.Multa_Repository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j // @Slf4j permite usar "log.info" para imprimir mensajes en la consola del servidor
@Transactional
@RequiredArgsConstructor
public class Multa_Service {
   private final Multa_Repository multaRepository;

    public List<MultaDTO> obtenerTodas() {
        log.info("Service: Obteniendo lista completa de multas");
        List<MultaDTO> multas = multaRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        log.debug("Se obtuvieron {} multas", multas.size());
        return multas;
    }

    public MultaDTO buscarPorId(Integer id) {
        log.info("Service: Buscando multa con ID: {}", id);
        Multa multa = multaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Multa no encontrada con ID: {}", id);
                    return new RuntimeException("Multa no encontrada con ID: " + id);
                });
        log.debug("Multa encontrada: ID {}", id);
        return convertirADTO(multa);
    }

    public MultaDTO crearMulta(MultaRequestDTO request) {
        log.info("Service: Creando nueva multa para usuario ID: {}", request.getUsuarioId());
        
        if (request.getUsuarioId() == null) {
            throw new RuntimeException("El ID del usuario es obligatorio");
        }
        if (request.getMonto() == null || request.getMonto() <= 0) {
            throw new RuntimeException("El monto de la multa debe ser mayor a 0");
        }

        Multa multa = Multa.builder()
                .usuarioId(request.getUsuarioId())
                .monto(request.getMonto())
                .motivo(request.getMotivo())
                .fechaGeneracion(LocalDate.now())
                .estado(Estado_Multa.PENDIENTE)
                .build();

        Multa guardada = multaRepository.save(multa);
        log.info("Multa creada exitosamente con ID: {}", guardada.getId());
        return convertirADTO(guardada);
    }

    public MultaDTO actualizar(Integer id, MultaRequestDTO request) {
        log.info("Service: Actualizando multa con ID: {}", id);
        
        Multa multa = multaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Multa no encontrada con ID: {}", id);
                    return new RuntimeException("Multa no encontrada con ID: " + id);
                });

        if (request.getMonto() != null && request.getMonto() > 0) {
            multa.setMonto(request.getMonto());
        }
        if (request.getMotivo() != null && !request.getMotivo().trim().isEmpty()) {
            multa.setMotivo(request.getMotivo());
        }

        Multa actualizada = multaRepository.save(multa);
        log.info("Multa actualizada exitosamente: ID {}", id);
        return convertirADTO(actualizada);
    }

    public void eliminar(Integer id) {
        log.info("Service: Eliminando multa con ID: {}", id);
        
        if (!multaRepository.existsById(id)) {
            log.error("Multa no encontrada con ID: {}", id);
            throw new RuntimeException("Multa no encontrada con ID: " + id);
        }
        
        multaRepository.deleteById(id);
        log.info("Multa eliminada exitosamente: ID {}", id);
    }

    private MultaDTO convertirADTO(Multa m) {
        return MultaDTO.builder()
                .id(m.getId())
                .usuarioId(m.getUsuarioId())
                .monto(m.getMonto())
                .motivo(m.getMotivo())
                .fechaGeneracion(m.getFechaGeneracion())
                .estado(m.getEstado())
                .build();
    }
}
