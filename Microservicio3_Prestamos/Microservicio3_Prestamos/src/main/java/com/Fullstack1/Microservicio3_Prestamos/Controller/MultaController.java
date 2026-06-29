package com.Fullstack1.Microservicio3_Prestamos.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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

import com.Fullstack1.Microservicio3_Prestamos.Assembler.MultaModelAssembler;
import com.Fullstack1.Microservicio3_Prestamos.DTO.MultaDTO;
import com.Fullstack1.Microservicio3_Prestamos.DTO.MultaRequestDTO;
import com.Fullstack1.Microservicio3_Prestamos.Service.MultaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/multas")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Multas", description = "Operaciones para gestionar las multas de los usuarios")
public class MultaController {
    
    private final MultaService multaService;
    private final MultaModelAssembler multaAssembler;

    @GetMapping
    @Operation(summary = "Listar todas las multas", description = "Obtiene el historial completo de multas registradas")
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Lista de multas obtenida exitosamente"),
    @ApiResponse(responseCode = "204", description = "No hay multas registradas en el sistema")
    })
    public ResponseEntity<CollectionModel<EntityModel<MultaDTO>>> listar() {
        log.info("REST Request: GET /api/v1/multas - Solicitud para listar todas las multas");
        List<MultaDTO> multas = multaService.obtenerTodas();
        if (multas.isEmpty()) {
            log.debug("No se encontraron multas en el sistema");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<EntityModel<MultaDTO>> multasModel = multas.stream()
            .map(multaAssembler::toModel)
            .collect(Collectors.toList());
        CollectionModel<EntityModel<MultaDTO>> collectionModel = CollectionModel.of(multasModel, linkTo(methodOn(MultaController.class).listar()).withSelfRel());            
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar multa por ID", description = "Retorna el detalle de una multa específica")
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Multa encontrada"),
    @ApiResponse(responseCode = "404", description = "Multa no encontrada")
    })
    public ResponseEntity<EntityModel<MultaDTO>> buscarPorId(@PathVariable Integer id) {
        log.info("REST Request: GET /api/v1/multas/{} - Buscando Multa", id);
        MultaDTO multa = multaService.buscarPorId(id);
        return ResponseEntity.ok(multaAssembler.toModel(multa));
    }

    @PostMapping
    @Operation(summary = "Crear nueva multa", description = "Registra una multa en el sistema y retorna código 201")
    @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Multa creada exitosamente"),
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos (Error de validación)")
    })
    public ResponseEntity<EntityModel<MultaDTO>> crear(@Valid @RequestBody MultaRequestDTO request) {
        log.info("REST Request: POST /api/v1/multas - Creando nueva multa");
        MultaDTO nuevaMulta = multaService.crearMulta(request);
        return new ResponseEntity<>(multaAssembler.toModel(nuevaMulta), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar multa", description = "Modifica el estado o los datos de una multa existente")
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Multa actualizada exitosamente"),
    @ApiResponse(responseCode = "404", description = "Multa no encontrada")
    })
    public ResponseEntity<EntityModel<MultaDTO>> actualizar(@PathVariable Integer id, @Valid @RequestBody MultaRequestDTO request) {
        log.info("REST Request: PUT /api/v1/multas/{} - Actualizando Multa", id);
        MultaDTO multaActualizada = multaService.actualizar(id, request);
        return ResponseEntity.ok(multaAssembler.toModel(multaActualizada));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar multa", description = "Borra físicamente una multa de la base de datos")
    @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Multa eliminada exitosamente (Sin contenido)"),
    @ApiResponse(responseCode = "404", description = "Multa no encontrada")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("REST Request: DELETE /api/v1/multas/{} - Eliminando Multa", id);
        multaService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
