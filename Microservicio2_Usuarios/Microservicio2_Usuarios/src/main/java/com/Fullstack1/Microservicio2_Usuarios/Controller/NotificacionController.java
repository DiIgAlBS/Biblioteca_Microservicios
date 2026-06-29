package com.Fullstack1.Microservicio2_Usuarios.Controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
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

import com.Fullstack1.Microservicio2_Usuarios.Assembler.NotificacionModelAssembler;
import com.Fullstack1.Microservicio2_Usuarios.DTO.NotificacionDTO;
import com.Fullstack1.Microservicio2_Usuarios.DTO.NotificacionRequestDTO;
import com.Fullstack1.Microservicio2_Usuarios.Service.NotificacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/notificaciones")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Notificaciones", description = "API para la gestión y envío de notificaciones a los usuarios") // 🌟 Agrupa los endpoints en Swagger
public class NotificacionController {

    private final NotificacionService notificacionService;
    private final NotificacionModelAssembler notificacionModelAssembler;

    @Operation(summary = "Listar todas las notificaciones", description = "Obtiene una lista completa de todas las notificaciones registradas en el sistema con sus respectivos enlaces HATEOAS.")
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Lista de notificaciones obtenida exitosamente"),
    @ApiResponse(responseCode = "204", description = "No hay notificaciones registradas en el sistema")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<NotificacionDTO>> listar() {
        log.info("REST Request: GET /api/v1/notificaciones - Solicitud para listar todas las notificaciones");
        List<NotificacionDTO> notificaciones = notificacionService.obtenerTodas();
        if (notificaciones.isEmpty()) {
            log.debug("No se encontraron notificaciones en el sistema");
            return ResponseEntity.noContent().build();
        }
        CollectionModel<NotificacionDTO> collectionModel = notificacionModelAssembler.toCollectionModel(notificaciones);
        collectionModel.add(linkTo(methodOn(NotificacionController.class).listar()).withSelfRel());
        return ResponseEntity.ok(collectionModel);
    }

    @Operation(summary = "Buscar notificación por ID", description = "Busca y retorna una notificación específica dado su identificador único.")
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Notificación encontrada exitosamente"),
    @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<NotificacionDTO> buscarPorId(@PathVariable Integer id) {
        log.info("REST Request: GET /api/v1/notificaciones/{} - Buscando Notificación", id);
        NotificacionDTO notificacion = notificacionService.buscarPorId(id);
        return ResponseEntity.ok(notificacionModelAssembler.toModel(notificacion));
    }

    @Operation(summary = "Obtener notificaciones por usuario", description = "Recupera la bandeja de entrada completa (todas las notificaciones) de un usuario específico.")
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Notificaciones del usuario obtenidas exitosamente"),
    @ApiResponse(responseCode = "204", description = "El usuario no tiene notificaciones")
    })
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<CollectionModel<NotificacionDTO>> notificacionesPorUsuario(@PathVariable Integer usuarioId) {
        log.info("REST Request: GET /api/v1/notificaciones/usuario/{} - Buscando notificaciones del usuario", usuarioId);
        List<NotificacionDTO> notificaciones = notificacionService.obtenerPorUsuarioId(usuarioId);
        if (notificaciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        CollectionModel<NotificacionDTO> collectionModel = notificacionModelAssembler.toCollectionModel(notificaciones);
        collectionModel.add(linkTo(methodOn(NotificacionController.class).notificacionesPorUsuario(usuarioId)).withSelfRel());
        return ResponseEntity.ok(collectionModel);
    }

    @Operation(summary = "Crear nueva notificación", description = "Registra y envía una nueva notificación a un usuario en el sistema.")
    @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Notificación creada exitosamente"),
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos (Validación fallida)")
    })
    @PostMapping
    public ResponseEntity<NotificacionDTO> crear(@Valid @RequestBody NotificacionRequestDTO request) {
        log.info("REST Request: POST /api/v1/notificaciones - Creando nueva notificación para usuario ID: {}", request.getUsuarioId());
        NotificacionDTO nuevaNotificacion = notificacionService.crearNotificacion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacionModelAssembler.toModel(nuevaNotificacion));
    }

    @Operation(summary = "Actualizar notificación", description = "Modifica los datos de una notificación existente (por ejemplo, para cambiar su mensaje o marcarla como leída).")
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Notificación actualizada exitosamente"),
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
    @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<NotificacionDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody NotificacionRequestDTO request) {
        log.info("REST Request: PUT /api/v1/notificaciones/{} - Actualizando Notificación", id);
        NotificacionDTO notificacionActualizada = notificacionService.actualizar(id, request);
        return ResponseEntity.ok(notificacionModelAssembler.toModel(notificacionActualizada));
    }

    @Operation(summary = "Eliminar notificación", description = "Elimina permanentemente una notificación del sistema.")
    @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Notificación eliminada exitosamente"),
    @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("REST Request: DELETE /api/v1/notificaciones/{} - Eliminando Notificación", id);
        notificacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
