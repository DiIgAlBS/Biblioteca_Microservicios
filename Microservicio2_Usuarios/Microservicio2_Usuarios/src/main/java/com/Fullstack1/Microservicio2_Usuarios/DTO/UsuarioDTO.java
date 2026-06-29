package com.Fullstack1.Microservicio2_Usuarios.DTO;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "Objeto de transferencia de datos que representa el perfil público y estado de un usuario")
public class UsuarioDTO extends RepresentationModel<UsuarioDTO> {

    @Schema(description = "ID único del usuario en el sistema", example = "10")
    private Integer id;

    @Schema(description = "Nombre completo del usuario", example = "Diego Alejandro Pérez")
    private String nombre;

    @Schema(description = "Dirección de correo electrónico institucional", example = "diego.perez@biblioteca.com")
    private String correo;

    @Schema(description = "Estado actual para el envío de alertas (ej: ACTIVO, SUSPENDIDO)", example = "ACTIVO")
    private String estadoNotificaciones; 

    @Schema(description = "Número telefónico o de contacto del usuario", example = "+56912345678")
    private String numero;

    @Schema(description = "Lista de IDs de los libros que el usuario tiene actualmente en préstamo", example = "[20, 25, 31]")
    private List<Integer> librosIds; 

    @Schema(description = "Lista con los títulos de los libros prestados (mapeo rápido para el Frontend)", example = "[\"Clean Code\", \"Java Software Structures\"]")
    private List<String> nombresLibros;
}
