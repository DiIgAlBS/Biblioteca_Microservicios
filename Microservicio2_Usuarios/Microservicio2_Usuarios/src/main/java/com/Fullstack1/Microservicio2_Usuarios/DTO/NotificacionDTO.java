package com.Fullstack1.Microservicio2_Usuarios.DTO;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import com.Fullstack1.Microservicio2_Usuarios.Model.TipoNotificacion;
import com.fasterxml.jackson.annotation.JsonFormat;

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
@Schema(description = "Objeto que representa una notificación enviada a un usuario")
public class NotificacionDTO extends RepresentationModel<NotificacionDTO> {
    
    @Schema(description = "ID único de la notificación", example = "1")
    private Integer id;
    
    @Schema(description = "ID del usuario receptor de la notificación", example = "15")
    private Integer usuarioId;
    
    @Schema(description = "Cuerpo del mensaje enviado", example = "Recuerde devolver el libro de Java mañana.")
    private String mensaje;
    
    @Schema(description = "Fecha de emisión de la notificación", example = "2026-06-28")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaEnvio;
    
    @Schema(description = "Estado de lectura de la notificación", example = "false")
    private Boolean leida;
    
    @Schema(description = "Categoría o tipo de la notificación", example = "RECORDATORIO")
    private TipoNotificacion tipo;
}
