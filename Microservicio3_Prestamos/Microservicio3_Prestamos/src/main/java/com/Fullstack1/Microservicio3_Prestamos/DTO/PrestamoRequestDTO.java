package com.Fullstack1.Microservicio3_Prestamos.DTO;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO con los datos necesarios para registrar una nueva solicitud de Préstamo")
public class PrestamoRequestDTO {

    @NotNull(message = "El ID del usuario es obligatorio")
    @Schema(description = "ID del usuario que solicita el préstamo (Debe existir en Microservicio2)", example = "10")
    private Integer usuarioId;

    @NotNull(message = "El ID del libro es obligatorio")
    @Schema(description = "ID del libro a prestar (Debe existir en Microservicio1)", example = "45")
    private Integer libroId;

    @NotNull(message = "La fecha de préstamo es obligatoria")
    @Schema(description = "Fecha de inicio del préstamo", example = "2026-06-28")
    private LocalDate fechaPrestamo;

    @NotNull(message = "La fecha de devolución es obligatoria")
    @Schema(description = "Fecha límite para devolver el libro", example = "2026-07-05")
    private LocalDate fechaDevolucion;
}
