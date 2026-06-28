package com.Fullstack1.Microservicio3_Prestamos.DTO;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // @Data (Lombok) genera automáticamente por debajo los Getters, Setters, toString y constructores
@Builder // @Builder (Lombok) habilita el patrón "Builder" para crear objetos de forma fluida paso a paso
@AllArgsConstructor // @AllArgsConstructor (Lombok): Crea un constructor con todos los atributos de la clase
@NoArgsConstructor // @NoArgsConstructor (Lombok): Crea un constructor vacío (obligatorio para que Spring trabaje bien)
public class PrestamoRequestDTO {
    @NotNull(message = "El ID de usuario es necesario") // Bloquea la petición si no nos envían a qué usuario le prestaremos el libro
    private Integer usuarioId;
    
    @NotNull(message = "El ID de libro es necesario") // Bloquea la petición si no nos envían qué libro exacto se van a llevar
    private Integer libroId;
    
    private LocalDate fechaDevolucion; // Fecha en la que planean devolver el libro (sin validación estricta aquí). La fecha de préstamo no está aquí porque la genera automáticamente el servidor.
}
