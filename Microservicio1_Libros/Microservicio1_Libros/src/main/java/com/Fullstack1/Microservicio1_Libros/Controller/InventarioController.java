package com.Fullstack1.Microservicio1_Libros.Controller;

import com.Fullstack1.Microservicio1_Libros.DTO.InventarioDTO;
import com.Fullstack1.Microservicio1_Libros.DTO.InventarioRequestDTO;
import com.Fullstack1.Microservicio1_Libros.Service.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/inventarios")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Inventarios", description = "Endpoints para gestionar inventarios")
public class InventarioController {
    
    private final InventarioService inventarioService;
    
    @GetMapping
    @Operation(summary = "Obtener todos los inventarios", description = "Retorna una lista de todos los inventarios registrados")
    public ResponseEntity<List<InventarioDTO>> obtenerTodos() {
        log.info("Controller: GET /api/v1/inventarios - Solicitando todos los inventarios");
        try {
            List<InventarioDTO> inventarios = inventarioService.obtenerTodos();
            log.info("Controller: Se retornaron {} inventarios", inventarios.size());
            return ResponseEntity.ok(inventarios);
        } catch (Exception e) {
            log.error("Controller: Error al obtener inventarios", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener inventario por ID", description = "Retorna un inventario específico por su ID")
    public ResponseEntity<InventarioDTO> obtenerPorId(
            @Parameter(description = "ID del inventario") @PathVariable Integer id) {
        log.info("Controller: GET /api/v1/inventarios/{} - Buscando inventario", id);
        try {
            InventarioDTO inventario = inventarioService.obtenerPorId(id);
            log.info("Controller: Inventario encontrado con ID: {}", id);
            return ResponseEntity.ok(inventario);
        } catch (RuntimeException e) {
            log.error("Controller: Inventario no encontrado con ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Controller: Error al obtener inventario", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping
    @Operation(summary = "Crear nuevo inventario", description = "Crea un nuevo inventario en el sistema")
    public ResponseEntity<InventarioDTO> crear(
            @Valid @RequestBody InventarioRequestDTO request) {
        log.info("Controller: POST /api/v1/inventarios - Creando nuevo inventario");
        try {
            InventarioDTO inventarioCreado = inventarioService.crear(request);
            log.info("Controller: Inventario creado exitosamente con ID: {}", inventarioCreado.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(inventarioCreado);
        } catch (RuntimeException e) {
            log.error("Controller: Error de validación al crear inventario: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Controller: Error al crear inventario", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar inventario", description = "Actualiza un inventario existente por su ID")
    public ResponseEntity<InventarioDTO> actualizar(
            @Parameter(description = "ID del inventario a actualizar") @PathVariable Integer id,
            @Valid @RequestBody InventarioRequestDTO request) {
        log.info("Controller: PUT /api/v1/inventarios/{} - Actualizando inventario", id);
        try {
            InventarioDTO inventarioActualizado = inventarioService.actualizar(id, request);
            log.info("Controller: Inventario actualizado exitosamente con ID: {}", id);
            return ResponseEntity.ok(inventarioActualizado);
        } catch (RuntimeException e) {
            log.error("Controller: Validación fallida al actualizar inventario: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Controller: Error al actualizar inventario", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar inventario", description = "Elimina un inventario del sistema por su ID")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del inventario a eliminar") @PathVariable Integer id) {
        log.info("Controller: DELETE /api/v1/inventarios/{} - Eliminando inventario", id);
        try {
            inventarioService.eliminar(id);
            log.info("Controller: Inventario eliminado exitosamente con ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Controller: Inventario no encontrado para eliminar: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Controller: Error al eliminar inventario", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
