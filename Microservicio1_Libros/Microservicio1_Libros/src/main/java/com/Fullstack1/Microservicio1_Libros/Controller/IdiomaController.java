package com.Fullstack1.Microservicio1_Libros.Controller;

import com.Fullstack1.Microservicio1_Libros.DTO.IdiomaDTO;
import com.Fullstack1.Microservicio1_Libros.DTO.IdiomaRequestDTO;
import com.Fullstack1.Microservicio1_Libros.Service.IdiomaService;
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
@RequestMapping("/api/v1/idiomas")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Idiomas", description = "Endpoints para gestionar idiomas")
public class IdiomaController {
    
    private final IdiomaService idiomaService;
    
    @GetMapping
    @Operation(summary = "Obtener todos los idiomas", description = "Retorna una lista de todos los idiomas registrados")
    public ResponseEntity<List<IdiomaDTO>> obtenerTodos() {
        log.info("Controller: GET /api/v1/idiomas - Solicitando todos los idiomas");
        try {
            List<IdiomaDTO> idiomas = idiomaService.obtenerTodos();
            log.info("Controller: Se retornaron {} idiomas", idiomas.size());
            return ResponseEntity.ok(idiomas);
        } catch (Exception e) {
            log.error("Controller: Error al obtener idiomas", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener idioma por ID", description = "Retorna un idioma específico por su ID")
    public ResponseEntity<IdiomaDTO> obtenerPorId(
            @Parameter(description = "ID del idioma") @PathVariable Integer id) {
        log.info("Controller: GET /api/v1/idiomas/{} - Buscando idioma", id);
        try {
            IdiomaDTO idioma = idiomaService.obtenerPorId(id);
            log.info("Controller: Idioma encontrado con ID: {}", id);
            return ResponseEntity.ok(idioma);
        } catch (RuntimeException e) {
            log.error("Controller: Idioma no encontrado con ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Controller: Error al obtener idioma", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/nombre/{nombre}")
    @Operation(summary = "Buscar idioma por nombre", description = "Retorna un idioma específico por su nombre")
    public ResponseEntity<IdiomaDTO> obtenerPorNombre(
            @Parameter(description = "Nombre del idioma") @PathVariable String nombre) {
        log.info("Controller: GET /api/v1/idiomas/nombre/{} - Buscando idioma", nombre);
        try {
            IdiomaDTO idioma = idiomaService.obtenerPorNombre(nombre);
            log.info("Controller: Idioma encontrado con nombre: {}", nombre);
            return ResponseEntity.ok(idioma);
        } catch (RuntimeException e) {
            log.error("Controller: Idioma no encontrado con nombre: {}", nombre);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Controller: Error al buscar por nombre", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping
    @Operation(summary = "Crear nuevo idioma", description = "Crea un nuevo idioma en el sistema")
    public ResponseEntity<IdiomaDTO> crear(
            @Valid @RequestBody IdiomaRequestDTO request) {
        log.info("Controller: POST /api/v1/idiomas - Creando nuevo idioma");
        try {
            IdiomaDTO idiomaCreado = idiomaService.crear(request);
            log.info("Controller: Idioma creado exitosamente con ID: {}", idiomaCreado.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(idiomaCreado);
        } catch (RuntimeException e) {
            log.error("Controller: Error de validación al crear idioma: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Controller: Error al crear idioma", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar idioma", description = "Actualiza un idioma existente por su ID")
    public ResponseEntity<IdiomaDTO> actualizar(
            @Parameter(description = "ID del idioma a actualizar") @PathVariable Integer id,
            @Valid @RequestBody IdiomaRequestDTO request) {
        log.info("Controller: PUT /api/v1/idiomas/{} - Actualizando idioma", id);
        try {
            IdiomaDTO idiomaActualizado = idiomaService.actualizar(id, request);
            log.info("Controller: Idioma actualizado exitosamente con ID: {}", id);
            return ResponseEntity.ok(idiomaActualizado);
        } catch (RuntimeException e) {
            log.error("Controller: Validación fallida al actualizar idioma: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Controller: Error al actualizar idioma", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar idioma", description = "Elimina un idioma del sistema por su ID")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del idioma a eliminar") @PathVariable Integer id) {
        log.info("Controller: DELETE /api/v1/idiomas/{} - Eliminando idioma", id);
        try {
            idiomaService.eliminar(id);
            log.info("Controller: Idioma eliminado exitosamente con ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Controller: Idioma no encontrado para eliminar: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Controller: Error al eliminar idioma", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
