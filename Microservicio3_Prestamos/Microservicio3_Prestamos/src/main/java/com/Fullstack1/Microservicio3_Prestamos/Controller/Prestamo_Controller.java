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

import com.Fullstack1.Microservicio3_Prestamos.DTO.PrestamoDTO;
import com.Fullstack1.Microservicio3_Prestamos.DTO.PrestamoRequestDTO;
import com.Fullstack1.Microservicio3_Prestamos.Service.Prestamo_Service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/prestamos")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Préstamos", description = "Controlador para la gestión y control del ciclo de vida de los préstamos de libros")
public class Prestamo_Controller {
    private final Prestamo_Service prestamoService;

    @GetMapping
    @Operation(summary = "Obtener todos los préstamos", description = "Retorna una lista completa con todos los registros de préstamos históricos y activos en el sistema")
    public ResponseEntity<List<PrestamoDTO>> listar() {
        log.info("REST Request: GET /api/v1/prestamos - Solicitud para listar todos los préstamos");
        List<PrestamoDTO> prestamos = prestamoService.obtenerTodos();
        if (prestamos.isEmpty()) {
            log.debug("No se encontraron préstamos en el sistema");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.debug("Se encontraron {} préstamos", prestamos.size());
        return ResponseEntity.ok(prestamos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar préstamo por ID", description = "Busca en la base de datos un préstamo específico utilizando su identificador numérico")
    public ResponseEntity<PrestamoDTO> buscarPorId(@PathVariable Integer id) {
        log.info("REST Request: GET /api/v1/prestamos/{} - Buscando Préstamo con ID: {}", id, id);
        try {
            PrestamoDTO prestamo = prestamoService.buscarPorId(id);
            log.debug("Préstamo encontrado: ID {}", id);
            return ResponseEntity.ok(prestamo);
        } catch (RuntimeException e) {
            log.error("Préstamo no encontrado con ID: {}", id);
            throw e;
        }
    }

    @PostMapping
    @Operation(summary = "Crear nuevo préstamo", description = "Registra un nuevo préstamo de libro en el sistema y retorna el objeto creado con código 201")
    public ResponseEntity<PrestamoDTO> crear(@Valid @RequestBody PrestamoRequestDTO request) {
        log.info("REST Request: POST /api/v1/prestamos - Creando nuevo préstamo para usuario ID: {}", request.getUsuarioId());
        PrestamoDTO nuevoPrestamo = prestamoService.crearPrestamo(request);
        log.info("Préstamo creado exitosamente con ID: {}", nuevoPrestamo.getId());
        return new ResponseEntity<>(nuevoPrestamo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un préstamo por ID", description = "Modifica los datos de un préstamo existente (como fechas o estados) según el ID proporcionado")
    public ResponseEntity<PrestamoDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody PrestamoRequestDTO request) {
        log.info("REST Request: PUT /api/v1/prestamos/{} - Actualizando Préstamo con ID: {}", id, id);
        try {
            PrestamoDTO prestamoActualizado = prestamoService.actualizar(id, request);
            log.info("Préstamo actualizado exitosamente: ID {}", id);
            return ResponseEntity.ok(prestamoActualizado);
        } catch (RuntimeException e) {
            log.error("Error al actualizar préstamo ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un préstamo por ID", description = "Remueve físicamente el registro de un préstamo de la base de datos utilizando su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("REST Request: DELETE /api/v1/prestamos/{} - Eliminando Préstamo con ID: {}", id, id);
        try {
            prestamoService.eliminar(id);
            log.info("Préstamo eliminado exitosamente: ID {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            log.error("Error al eliminar préstamo ID {}: {}", id, e.getMessage());
            throw e;
        }
    }
}
