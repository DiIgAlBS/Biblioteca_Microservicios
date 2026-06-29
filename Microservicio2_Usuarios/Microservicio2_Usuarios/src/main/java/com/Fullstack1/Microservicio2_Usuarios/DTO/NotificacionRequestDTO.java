package com.Fullstack1.Microservicio2_Usuarios.DTO;

import com.Fullstack1.Microservicio2_Usuarios.Model.TipoNotificacion;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Objeto de transferencia de datos para la creación o actualización de una notificación")
public class NotificacionRequestDTO {

    @NotNull(message = "El ID de usuario es obligatorio")
    @Schema(description = "Identificador único del usuario que recibirá la notificación", example = "45", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer usuarioId;

    @NotBlank(message = "El mensaje no puede estar vacío")
    @Size(min = 5, max = 500, message = "El mensaje debe tener entre 5 y 500 caracteres") // Detalle minucioso: Protección de BD y coherencia de datos
    @Schema(description = "Contenido o cuerpo del mensaje de la notificación", example = "Su préstamo del libro 'Clean Code' vence mañana.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mensaje;

    @NotNull(message = "Debe especificar el tipo de notificación")
    @Schema(description = "Categoría o criticidad de la notificación", example = "RECORDATORIO", requiredMode = Schema.RequiredMode.REQUIRED)
    private TipoNotificacion tipo;
}
