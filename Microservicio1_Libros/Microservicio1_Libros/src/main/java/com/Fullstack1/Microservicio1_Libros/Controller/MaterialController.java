package com.Fullstack1.Microservicio1_Libros.Controller;

import com.Fullstack1.Microservicio1_Libros.DTO.MaterialDTO;
import com.Fullstack1.Microservicio1_Libros.DTO.MaterialRequestDTO;
import com.Fullstack1.Microservicio1_Libros.Service.MaterialService;
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
@RequestMapping("/api/v1/materiales")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Materiales", description = "Endpoints para gestionar materiales")
public class MaterialController {
    
    private final MaterialService materialService;
    
    @GetMapping
    @Operation(summary = "Obtener todos los materiales", description = "Retorna una lista de todos los materiales registrados")
    public ResponseEntity<List<MaterialDTO>> obtenerTodos() {
        log.info("Controller: GET /api/v1/materiales - Solicitando todos los materiales");
        try {
            List<MaterialDTO> materiales = materialService.obtenerTodos();
            log.info("Controller: Se retornaron {} materiales", materiales.size());
            return ResponseEntity.ok(materiales);
        } catch (Exception e) {
            log.error("Controller: Error al obtener materiales", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener material por ID", description = "Retorna un material específico por su ID")
    public ResponseEntity<MaterialDTO> obtenerPorId(
            @Parameter(description = "ID del material") @PathVariable Integer id) {
        log.info("Controller: GET /api/v1/materiales/{} - Buscando material", id);
        try {
            MaterialDTO material = materialService.obtenerPorId(id);
            log.info("Controller: Material encontrado con ID: {}", id);
            return ResponseEntity.ok(material);
        } catch (RuntimeException e) {
            log.error("Controller: Material no encontrado con ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Controller: Error al obtener material", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/libro/{libroId}")
    @Operation(summary = "Obtener materiales por libro", description = "Retorna una lista de materiales de un libro específico")
    public ResponseEntity<List<MaterialDTO>> obtenerPorLibro(
            @Parameter(description = "ID del libro") @PathVariable Integer libroId) {
        log.info("Controller: GET /api/v1/materiales/libro/{} - Obteniendo materiales del libro", libroId);
        try {
            List<MaterialDTO> materiales = materialService.obtenerPorLibro(libroId);
            log.info("Controller: Se encontraron {} materiales del libro", materiales.size());
            return ResponseEntity.ok(materiales);
        } catch (Exception e) {
            log.error("Controller: Error al obtener materiales del libro", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/estado/{estado}")
    @Operation(summary = "Obtener materiales por estado", description = "Retorna una lista de materiales filtrados por estado")
    public ResponseEntity<List<MaterialDTO>> obtenerPorEstado(
            @Parameter(description = "Estado del material (DISPONIBLE, NO_DISPONIBLE, EN_MANTENIMIENTO)") @PathVariable String estado) {
        log.info("Controller: GET /api/v1/materiales/estado/{} - Obteniendo materiales por estado", estado);
        try {
            List<MaterialDTO> materiales = materialService.obtenerPorEstado(estado);
            log.info("Controller: Se encontraron {} materiales con estado: {}", materiales.size(), estado);
            return ResponseEntity.ok(materiales);
        } catch (Exception e) {
            log.error("Controller: Error al obtener materiales por estado", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping
    @Operation(summary = "Crear nuevo material", description = "Crea un nuevo material en el sistema")
    public ResponseEntity<MaterialDTO> crear(
            @Valid @RequestBody MaterialRequestDTO request) {
        log.info("Controller: POST /api/v1/materiales - Creando nuevo material");
        try {
            MaterialDTO materialCreado = materialService.crear(request);
            log.info("Controller: Material creado exitosamente con ID: {}", materialCreado.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(materialCreado);
        } catch (RuntimeException e) {
            log.error("Controller: Error de validación al crear material: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Controller: Error al crear material", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar material", description = "Actualiza un material existente por su ID")
    public ResponseEntity<MaterialDTO> actualizar(
            @Parameter(description = "ID del material a actualizar") @PathVariable Integer id,
            @Valid @RequestBody MaterialRequestDTO request) {
        log.info("Controller: PUT /api/v1/materiales/{} - Actualizando material", id);
        try {
            MaterialDTO materialActualizado = materialService.actualizar(id, request);
            log.info("Controller: Material actualizado exitosamente con ID: {}", id);
            return ResponseEntity.ok(materialActualizado);
        } catch (RuntimeException e) {
            log.error("Controller: Validación fallida al actualizar material: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Controller: Error al actualizar material", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar material", description = "Elimina un material del sistema por su ID")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del material a eliminar") @PathVariable Integer id) {
        log.info("Controller: DELETE /api/v1/materiales/{} - Eliminando material", id);
        try {
            materialService.eliminar(id);
            log.info("Controller: Material eliminado exitosamente con ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Controller: Material no encontrado para eliminar: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Controller: Error al eliminar material", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
