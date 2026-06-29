package com.Fullstack1.Microservicio3_Prestamos.DTO;

import java.time.LocalDate;

import com.Fullstack1.Microservicio3_Prestamos.Model.EstadoPrestamo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa la respuesta completa de un Préstamo (Lectura)")
public class PrestamoDTO {

    @Schema(description = "ID único del préstamo", example = "1")
    private Integer id;

    @Schema(description = "ID del usuario que solicitó el libro", example = "10")
    private Integer usuarioId;

    @Schema(description = "ID del libro prestado", example = "45")
    private Integer libroId;

    @Schema(description = "Fecha en la que el usuario retiró el libro", example = "2026-06-28")
    private LocalDate fechaPrestamo; 

    @Schema(description = "Fecha límite acordada para retornar el libro", example = "2026-07-05")
    private LocalDate fechaDevolucion;

    @Schema(description = "Estado actual del ciclo del préstamo", example = "ACTIVO")
    private EstadoPrestamo estado;
}
