package com.Fullstack1.Microservicio1_Libros.Controller;

import com.Fullstack1.Microservicio1_Libros.DTO.AutorDTO;
import com.Fullstack1.Microservicio1_Libros.DTO.AutorRequestDTO;
import com.Fullstack1.Microservicio1_Libros.Service.AutorService;
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
@RequestMapping("/api/v1/autores")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Autores", description = "Endpoints para gestionar autores")
public class AutorController {
    
    private final AutorService autorService;
    
    @GetMapping
    @Operation(summary = "Obtener todos los autores", description = "Retorna una lista de todos los autores registrados")
    public ResponseEntity<List<AutorDTO>> obtenerTodos() {
        log.info("Controller: GET /api/v1/autores - Solicitando todos los autores");
        try {
            List<AutorDTO> autores = autorService.obtenerTodos();
            log.info("Controller: Se retornaron {} autores", autores.size());
            return ResponseEntity.ok(autores);
        } catch (Exception e) {
            log.error("Controller: Error al obtener autores", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener autor por ID", description = "Retorna un autor específico por su ID")
    public ResponseEntity<AutorDTO> obtenerPorId(
            @Parameter(description = "ID del autor") @PathVariable Integer id) {
        log.info("Controller: GET /api/v1/autores/{} - Buscando autor", id);
        try {
            AutorDTO autor = autorService.obtenerPorId(id);
            log.info("Controller: Autor encontrado con ID: {}", id);
            return ResponseEntity.ok(autor);
        } catch (RuntimeException e) {
            log.error("Controller: Autor no encontrado con ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Controller: Error al obtener autor", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/rut/{rut}")
    @Operation(summary = "Buscar autor por RUT", description = "Retorna un autor específico por su RUT")
    public ResponseEntity<AutorDTO> obtenerPorRut(
            @Parameter(description = "RUT del autor") @PathVariable String rut) {
        log.info("Controller: GET /api/v1/autores/rut/{} - Buscando autor por RUT", rut);
        try {
            AutorDTO autor = autorService.obtenerPorRut(rut);
            log.info("Controller: Autor encontrado con RUT: {}", rut);
            return ResponseEntity.ok(autor);
        } catch (RuntimeException e) {
            log.error("Controller: Autor no encontrado con RUT: {}", rut);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Controller: Error al buscar por RUT", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/nombre/{nombre}")
    @Operation(summary = "Buscar autores por nombre", description = "Retorna una lista de autores que coincidan con el nombre")
    public ResponseEntity<List<AutorDTO>> buscarPorNombre(
            @Parameter(description = "Nombre del autor a buscar") @PathVariable String nombre) {
        log.info("Controller: GET /api/v1/autores/nombre/{} - Buscando autores", nombre);
        try {
            List<AutorDTO> autores = autorService.buscarPorNombre(nombre);
            log.info("Controller: Se encontraron {} autores con nombre: {}", autores.size(), nombre);
            return ResponseEntity.ok(autores);
        } catch (Exception e) {
            log.error("Controller: Error en búsqueda por nombre", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    
    @PostMapping
    @Operation(summary = "Crear nuevo autor", description = "Crea un nuevo autor en el sistema")
    public ResponseEntity<AutorDTO> crear(
            @Valid @RequestBody AutorRequestDTO request) {
        log.info("Controller: POST /api/v1/autores - Creando nuevo autor");
        try {
            AutorDTO autorCreado = autorService.crear(request);
            log.info("Controller: Autor creado exitosamente con ID: {}", autorCreado.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(autorCreado);
        } catch (RuntimeException e) {
            log.error("Controller: Error de validación al crear autor: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Controller: Error al crear autor", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar autor", description = "Actualiza un autor existente por su ID")
    public ResponseEntity<AutorDTO> actualizar(
            @Parameter(description = "ID del autor a actualizar") @PathVariable Integer id,
            @Valid @RequestBody AutorRequestDTO request) {
        log.info("Controller: PUT /api/v1/autores/{} - Actualizando autor", id);
        try {
            AutorDTO autorActualizado = autorService.actualizar(id, request);
            log.info("Controller: Autor actualizado exitosamente con ID: {}", id);
            return ResponseEntity.ok(autorActualizado);
        } catch (RuntimeException e) {
            log.error("Controller: Validación fallida al actualizar autor: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Controller: Error al actualizar autor", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
  
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar autor", description = "Elimina un autor del sistema por su ID")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del autor a eliminar") @PathVariable Integer id) {
        log.info("Controller: DELETE /api/v1/autores/{} - Eliminando autor", id);
        try {
            autorService.eliminar(id);
            log.info("Controller: Autor eliminado exitosamente con ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Controller: Autor no encontrado para eliminar: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Controller: Error al eliminar autor", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
