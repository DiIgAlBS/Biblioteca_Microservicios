package com.Fullstack1.Microservicio1_Libros.Controller;

import com.Fullstack1.Microservicio1_Libros.DTO.EditorialDTO;
import com.Fullstack1.Microservicio1_Libros.DTO.EditorialRequestDTO;
import com.Fullstack1.Microservicio1_Libros.Service.EditorialService;
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
@RequestMapping("/api/v1/editoriales")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Editoriales", description = "Endpoints para gestionar editoriales")
public class EditorialController {
    
    private final EditorialService editorialService;
    
    @GetMapping
    @Operation(summary = "Obtener todas las editoriales", description = "Retorna una lista de todas las editoriales registradas")
    public ResponseEntity<List<EditorialDTO>> obtenerTodos() {
        log.info("Controller: GET /api/v1/editoriales - Solicitando todas las editoriales");
        try {
            List<EditorialDTO> editoriales = editorialService.obtenerTodos();
            log.info("Controller: Se retornaron {} editoriales", editoriales.size());
            return ResponseEntity.ok(editoriales);
        } catch (Exception e) {
            log.error("Controller: Error al obtener editoriales", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener editorial por ID", description = "Retorna una editorial específica por su ID")
    public ResponseEntity<EditorialDTO> obtenerPorId(
            @Parameter(description = "ID de la editorial") @PathVariable Integer id) {
        log.info("Controller: GET /api/v1/editoriales/{} - Buscando editorial", id);
        try {
            EditorialDTO editorial = editorialService.obtenerPorId(id);
            log.info("Controller: Editorial encontrada con ID: {}", id);
            return ResponseEntity.ok(editorial);
        } catch (RuntimeException e) {
            log.error("Controller: Editorial no encontrada con ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Controller: Error al obtener editorial", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/nombre/{nombre}")
    @Operation(summary = "Buscar editorial por nombre", description = "Retorna una editorial específica por su nombre")
    public ResponseEntity<EditorialDTO> obtenerPorNombre(
            @Parameter(description = "Nombre de la editorial") @PathVariable String nombre) {
        log.info("Controller: GET /api/v1/editoriales/nombre/{} - Buscando editorial", nombre);
        try {
            EditorialDTO editorial = editorialService.obtenerPorNombre(nombre);
            log.info("Controller: Editorial encontrada con nombre: {}", nombre);
            return ResponseEntity.ok(editorial);
        } catch (RuntimeException e) {
            log.error("Controller: Editorial no encontrada con nombre: {}", nombre);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Controller: Error al buscar por nombre", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping
    @Operation(summary = "Crear nueva editorial", description = "Crea una nueva editorial en el sistema")
    public ResponseEntity<EditorialDTO> crear(
            @Valid @RequestBody EditorialRequestDTO request) {
        log.info("Controller: POST /api/v1/editoriales - Creando nueva editorial");
        try {
            EditorialDTO editorialCreada = editorialService.crear(request);
            log.info("Controller: Editorial creada exitosamente con ID: {}", editorialCreada.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(editorialCreada);
        } catch (RuntimeException e) {
            log.error("Controller: Error de validación al crear editorial: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Controller: Error al crear editorial", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar editorial", description = "Actualiza una editorial existente por su ID")
    public ResponseEntity<EditorialDTO> actualizar(
            @Parameter(description = "ID de la editorial a actualizar") @PathVariable Integer id,
            @Valid @RequestBody EditorialRequestDTO request) {
        log.info("Controller: PUT /api/v1/editoriales/{} - Actualizando editorial", id);
        try {
            EditorialDTO editorialActualizada = editorialService.actualizar(id, request);
            log.info("Controller: Editorial actualizada exitosamente con ID: {}", id);
            return ResponseEntity.ok(editorialActualizada);
        } catch (RuntimeException e) {
            log.error("Controller: Validación fallida al actualizar editorial: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Controller: Error al actualizar editorial", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar editorial", description = "Elimina una editorial del sistema por su ID")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la editorial a eliminar") @PathVariable Integer id) {
        log.info("Controller: DELETE /api/v1/editoriales/{} - Eliminando editorial", id);
        try {
            editorialService.eliminar(id);
            log.info("Controller: Editorial eliminada exitosamente con ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Controller: Editorial no encontrada para eliminar: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Controller: Error al eliminar editorial", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
