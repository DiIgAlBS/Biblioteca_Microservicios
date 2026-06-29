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

import com.Fullstack1.Microservicio2_Usuarios.Assembler.UsuarioModelAssembler;
import com.Fullstack1.Microservicio2_Usuarios.DTO.UsuarioDTO;
import com.Fullstack1.Microservicio2_Usuarios.DTO.UsuarioRequestDTO;
import com.Fullstack1.Microservicio2_Usuarios.Service.UsuarioService;

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
@RequestMapping("/api/v1/usuarios")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "API para la gestión integral de los usuarios del sistema")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioModelAssembler usuarioModelAssembler;

    @Operation(summary = "Listar todos los usuarios", description = "Obtiene una lista completa de los usuarios registrados con sus respectivos enlaces HATEOAS.")
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente"),
    @ApiResponse(responseCode = "204", description = "No hay usuarios registrados en el sistema")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<UsuarioDTO>> todosLosUsuarios() {
        log.info("REST Request: GET /api/v1/usuarios - Solicitud para listar todos los usuarios");
        List<UsuarioDTO> usuarios = usuarioService.obtenerTodos();
        
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        CollectionModel<UsuarioDTO> collectionModel = usuarioModelAssembler.toCollectionModel(usuarios);
        collectionModel.add(linkTo(methodOn(UsuarioController.class).todosLosUsuarios()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    @Operation(summary = "Buscar usuario por ID", description = "Busca y retorna la información de un usuario específico mediante su identificador.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Integer id) {
        log.info("REST Request: GET /api/v1/usuarios/{} - Buscando Usuario por ID", id);
        UsuarioDTO usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuarioModelAssembler.toModel(usuario));
    }

    @Operation(summary = "Crear nuevo usuario", description = "Registra un nuevo usuario en la base de datos del sistema.")
    @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos (Validación fallida)")
    })
    @PostMapping
    public ResponseEntity<UsuarioDTO> guardarUsuario(@Valid @RequestBody UsuarioRequestDTO dto) {
        log.info("REST Request: POST /api/v1/usuarios - Registrando nuevo usuario: {}", dto.getNombre());
        UsuarioDTO usuarioGuardado = usuarioService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioModelAssembler.toModel(usuarioGuardado));
    }

    @Operation(summary = "Actualizar usuario", description = "Modifica los datos personales de un usuario existente.")
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable Integer id, @Valid @RequestBody UsuarioRequestDTO dto) {
        log.info("REST Request: PUT /api/v1/usuarios/{} - Actualizando datos de usuario", id);
        UsuarioDTO actualizado = usuarioService.actualizar(id, dto);
        return ResponseEntity.ok(usuarioModelAssembler.toModel(actualizado));
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina permanentemente a un usuario del sistema.")
    @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        log.info("REST Request: DELETE /api/v1/usuarios/{} - Eliminando usuario", id);
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
