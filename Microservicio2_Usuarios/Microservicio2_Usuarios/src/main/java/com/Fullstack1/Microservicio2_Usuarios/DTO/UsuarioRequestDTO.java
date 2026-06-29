package com.Fullstack1.Microservicio2_Usuarios.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Objeto de transferencia de datos para registrar o actualizar la información de un usuario")
public class UsuarioRequestDTO {

    @NotBlank(message = "Es obligatorio ingresar el nombre del usuario")
    @Size(min = 1, max = 50, message = "El nombre debe tener entre 1 y 50 caracteres")
    @Schema(description = "Nombre completo del usuario", example = "Diego Alejandro Pérez", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ingresar un formato de correo válido")
    @Size(max = 50, message = "El correo no puede superar los 50 caracteres")
    @Schema(description = "Dirección de correo electrónico única y válida", example = "diego.perez@biblioteca.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String correo;

    @NotBlank(message = "El número de teléfono es obligatorio")
    @Pattern(regexp = "^\\+?[0-9]{9,15}$", message = "El número de teléfono no tiene un formato válido (ejemplos válidos: +56912345678 o 912345678)")
    @Schema(description = "Número telefónico de contacto con código de país opcional (entre 9 y 15 dígitos)", example = "+56912345678", requiredMode = Schema.RequiredMode.REQUIRED)
    private String numero;
}
