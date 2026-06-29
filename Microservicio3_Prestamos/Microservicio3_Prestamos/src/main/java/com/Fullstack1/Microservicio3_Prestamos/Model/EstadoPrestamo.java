package com.Fullstack1.Microservicio3_Prestamos.Model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ciclo de vida estricto y estados posibles de un préstamo en el sistema")
public enum EstadoPrestamo {
    @Schema(description = "El libro está en manos del usuario y todavía está a tiempo de devolverlo dentro del plazo legal")
    ACTIVO, 
    @Schema(description = "El ciclo terminó con éxito: el usuario entregó el libro en la biblioteca")
    DEVUELTO, 
    @Schema(description = "El ciclo falló: el usuario aún tiene el libro y ya superó la fecha límite establecida")
    ATRASADO 
}
