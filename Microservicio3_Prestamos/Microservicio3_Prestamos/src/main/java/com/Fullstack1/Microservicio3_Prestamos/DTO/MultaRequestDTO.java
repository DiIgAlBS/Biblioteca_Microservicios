package com.Fullstack1.Microservicio3_Prestamos.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO con los datos requeridos para crear o actualizar una Multa")
public class MultaRequestDTO {

    @NotNull(message = "El ID del préstamo es obligatorio")
    @Schema(description = "ID del préstamo asociado que generó la infracción", example = "142")
    private Integer prestamoId;

    @NotNull(message = "El ID del usuario es obligatorio")
    @Schema(description = "ID del usuario penalizado", example = "5")
    private Integer usuarioId;

    @NotNull(message = "El monto de la multa no puede ser nulo")
    @Positive(message = "El monto de la multa debe ser un valor mayor a cero")
    @Schema(description = "Monto económico de la sanción", example = "3500.0")
    private Double monto;

    @NotBlank(message = "El motivo de la multa no puede estar vacío")
    @Size(min = 10, max = 255, message = "El motivo debe tener entre 10 y 255 caracteres")
    @Schema(description = "Explicación detallada de la multa", example = "Retraso de 3 días en la devolución del libro")
    private String motivo;
}
