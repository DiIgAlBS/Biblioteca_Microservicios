package com.Fullstack1.Microservicio3_Prestamos.DTO;

import java.time.LocalDate;

import com.Fullstack1.Microservicio3_Prestamos.Model.Estado_Prestamo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // @Data (Lombok) genera automáticamente por debajo los Getters, Setters, toString y constructores
@Builder // @Builder (Lombok) habilita el patrón "Builder" para crear objetos de forma fluida paso a paso
@AllArgsConstructor // @AllArgsConstructor (Lombok): Crea un constructor con todos los atributos de la clase
@NoArgsConstructor // @NoArgsConstructor (Lombok): Crea un constructor vacío (obligatorio para que Spring trabaje bien)
public class PrestamoDTO {
    private Integer id;
    private Integer usuarioId;
    private Integer libroId;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private Estado_Prestamo estado; // Estado actual del préstamo (ej. ACTIVO, DEVUELTO, ATRASADO)
}
