package com.Fullstack1.Microservicio3_Prestamos.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Fullstack1.Microservicio3_Prestamos.DTO.MultaDTO;
import com.Fullstack1.Microservicio3_Prestamos.DTO.MultaRequestDTO;
import com.Fullstack1.Microservicio3_Prestamos.Service.Multa_Service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/multas")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Multas", description = "Operaciones para gestionar las multas de los usuarios")
public class Multa_Controller {
    private final Multa_Service multaService;

    @GetMapping
    @Operation(summary = "Listar todas las multas", description = "Obtiene el historial completo de multas registradas")    
    public ResponseEntity<List<MultaDTO>> listar() {
        log.info("REST Request: GET /api/v1/multas - Solicitud para listar todas las multas");
        List<MultaDTO> multas = multaService.obtenerTodas();
        if (multas.isEmpty()) {
            log.debug("No se encontraron multas en el sistema");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(multas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar multa por ID", description = "Retorna el detalle de una multa específica")
    public ResponseEntity<MultaDTO> buscarPorId(@PathVariable Integer id) {
        log.info("REST Request: GET /api/v1/multas/{} - Buscando Multa con ID: {}", id, id);
        try {
            MultaDTO multa = multaService.buscarPorId(id);
            return ResponseEntity.ok(multa);
        } catch (RuntimeException e) {
            log.error("Multa no encontrada con ID: {}", id);
            throw e;
        }
    }

    @PostMapping
    @Operation(summary = "Crear nueva multa", description = "Registra una multa en el sistema y retorna código 201")
    public ResponseEntity<MultaDTO> crear(@Valid @RequestBody MultaRequestDTO request) {
        log.info("REST Request: POST /api/v1/multas - Creando nueva multa para usuario ID: {}", request.getUsuarioId());
        MultaDTO nuevaMulta = multaService.crearMulta(request);
        log.info("Multa creada exitosamente con ID: {}", nuevaMulta.getId());
        return new ResponseEntity<>(nuevaMulta, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar multa", description = "Modifica el estado o los datos de una multa existente")
    public ResponseEntity<MultaDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody MultaRequestDTO request) {
        log.info("REST Request: PUT /api/v1/multas/{} - Actualizando Multa con ID: {}", id, id);
        try {
            MultaDTO multaActualizada = multaService.actualizar(id, request);
            log.info("Multa actualizada exitosamente: ID {}", id);
            return ResponseEntity.ok(multaActualizada);
        } catch (RuntimeException e) {
            log.error("Error al actualizar multa ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar multa", description = "Borra físicamente una multa de la base de datos")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("REST Request: DELETE /api/v1/multas/{} - Eliminando Multa con ID: {}", id, id);
        try {
            multaService.eliminar(id);
            log.info("Multa eliminada exitosamente: ID {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            log.error("Error al eliminar multa ID {}: {}", id, e.getMessage());
            throw e;
        }
    }
}
