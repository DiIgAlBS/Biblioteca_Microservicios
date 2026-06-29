package com.Fullstack1.Microservicio1_Libros.Controller;

import com.Fullstack1.Microservicio1_Libros.DTO.LibroDTO;
import com.Fullstack1.Microservicio1_Libros.DTO.LibroRequestDTO;
import com.Fullstack1.Microservicio1_Libros.Service.LibroService;
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
@RequestMapping("/api/v1/libros")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Libros", description = "Endpoints para gestionar libros")
public class LibroController {
    
    private final LibroService libroService;
    
    @GetMapping
    @Operation(summary = "Obtener todos los libros", description = "Retorna una lista de todos los libros registrados")
    public ResponseEntity<List<LibroDTO>> obtenerTodos() {
        log.info("Controller: GET /api/v1/libros - Solicitando todos los libros");
        try {
            List<LibroDTO> libros = libroService.obtenerTodos();
            log.info("Controller: Se retornaron {} libros", libros.size());
            return ResponseEntity.ok(libros);
        } catch (Exception e) {
            log.error("Controller: Error al obtener libros", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener libro por ID", description = "Retorna un libro específico por su ID")
    public ResponseEntity<LibroDTO> obtenerPorId(
            @Parameter(description = "ID del libro") @PathVariable Integer id) {
        log.info("Controller: GET /api/v1/libros/{} - Buscando libro", id);
        try {
            LibroDTO libro = libroService.obtenerPorId(id);
            log.info("Controller: Libro encontrado con ID: {}", id);
            return ResponseEntity.ok(libro);
        } catch (RuntimeException e) {
            log.error("Controller: Libro no encontrado con ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Controller: Error al obtener libro", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/nombre/{nombre}")
    @Operation(summary = "Buscar libros por nombre", description = "Retorna una lista de libros que coincidan con el nombre")
    public ResponseEntity<List<LibroDTO>> buscarPorNombre(
            @Parameter(description = "Nombre del libro a buscar") @PathVariable String nombre) {
        log.info("Controller: GET /api/v1/libros/nombre/{} - Buscando libros", nombre);
        try {
            List<LibroDTO> libros = libroService.buscarPorNombre(nombre);
            log.info("Controller: Se encontraron {} libros con nombre: {}", libros.size(), nombre);
            return ResponseEntity.ok(libros);
        } catch (Exception e) {
            log.error("Controller: Error en búsqueda por nombre", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/autor/{autorId}")
    @Operation(summary = "Obtener libros por autor", description = "Retorna una lista de libros de un autor específico")
    public ResponseEntity<List<LibroDTO>> obtenerPorAutor(
            @Parameter(description = "ID del autor") @PathVariable Integer autorId) {
        log.info("Controller: GET /api/v1/libros/autor/{} - Obteniendo libros del autor", autorId);
        try {
            List<LibroDTO> libros = libroService.obtenerPorAutor(autorId);
            log.info("Controller: Se encontraron {} libros del autor", libros.size());
            return ResponseEntity.ok(libros);
        } catch (Exception e) {
            log.error("Controller: Error al obtener libros del autor", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/editorial/{editorialId}")
    @Operation(summary = "Obtener libros por editorial", description = "Retorna una lista de libros de una editorial específica")
    public ResponseEntity<List<LibroDTO>> obtenerPorEditorial(
            @Parameter(description = "ID de la editorial") @PathVariable Integer editorialId) {
        log.info("Controller: GET /api/v1/libros/editorial/{} - Obteniendo libros de la editorial", editorialId);
        try {
            List<LibroDTO> libros = libroService.obtenerPorEditorial(editorialId);
            log.info("Controller: Se encontraron {} libros de la editorial", libros.size());
            return ResponseEntity.ok(libros);
        } catch (Exception e) {
            log.error("Controller: Error al obtener libros de la editorial", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping
    @Operation(summary = "Crear nuevo libro", description = "Crea un nuevo libro en el sistema")
    public ResponseEntity<LibroDTO> crear(
            @Valid @RequestBody LibroRequestDTO request) {
        log.info("Controller: POST /api/v1/libros - Creando nuevo libro");
        try {
            LibroDTO libroCreado = libroService.crear(request);
            log.info("Controller: Libro creado exitosamente con ID: {}", libroCreado.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(libroCreado);
        } catch (RuntimeException e) {
            log.error("Controller: Error de validación al crear libro: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Controller: Error al crear libro", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar libro", description = "Actualiza un libro existente por su ID")
    public ResponseEntity<LibroDTO> actualizar(
            @Parameter(description = "ID del libro a actualizar") @PathVariable Integer id,
            @Valid @RequestBody LibroRequestDTO request) {
        log.info("Controller: PUT /api/v1/libros/{} - Actualizando libro", id);
        try {
            LibroDTO libroActualizado = libroService.actualizar(id, request);
            log.info("Controller: Libro actualizado exitosamente con ID: {}", id);
            return ResponseEntity.ok(libroActualizado);
        } catch (RuntimeException e) {
            log.error("Controller: Validación fallida al actualizar libro: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Controller: Error al actualizar libro", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar libro", description = "Elimina un libro del sistema por su ID")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del libro a eliminar") @PathVariable Integer id) {
        log.info("Controller: DELETE /api/v1/libros/{} - Eliminando libro", id);
        try {
            libroService.eliminar(id);
            log.info("Controller: Libro eliminado exitosamente con ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Controller: Libro no encontrado para eliminar: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Controller: Error al eliminar libro", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
