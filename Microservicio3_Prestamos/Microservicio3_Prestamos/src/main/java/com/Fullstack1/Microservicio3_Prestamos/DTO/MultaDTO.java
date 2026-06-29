package com.Fullstack1.Microservicio3_Prestamos.DTO;

import java.time.LocalDate;

import com.Fullstack1.Microservicio3_Prestamos.Model.EstadoMulta;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa la respuesta completa de una Multa (Lectura)")
public class MultaDTO {

    @Schema(description = "ID único de la multa", example = "1")
    private Integer id;

    @Schema(description = "ID del préstamo asociado", example = "142")
    private Integer prestamoId;

    @Schema(description = "ID del usuario penalizado", example = "5")
    private Integer usuarioId;

    @Schema(description = "Monto económico de la sanción", example = "3500.0")
    private Double monto;

    @Schema(description = "Explicación detallada de la infracción", example = "Retraso de 3 días en la devolución del libro")
    private String motivo;

    @Schema(description = "Fecha de emisión automática del sistema", example = "2026-06-28")
    private LocalDate fechaGeneracion;

    @Schema(description = "Estado actual de la multa", example = "PENDIENTE")
    private EstadoMulta estado;
}
