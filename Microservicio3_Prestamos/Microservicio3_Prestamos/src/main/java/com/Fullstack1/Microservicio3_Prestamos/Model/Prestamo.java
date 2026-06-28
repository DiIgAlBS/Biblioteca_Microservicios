package com.Fullstack1.Microservicio3_Prestamos.Model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // @Data (Lombok) genera automáticamente por debajo los Getters, Setters, toString y constructores
@AllArgsConstructor // @AllArgsConstructor (Lombok): Crea un constructor con todos los atributos de la clase
@NoArgsConstructor // @NoArgsConstructor (Lombok): Crea un constructor vacío (obligatorio para que Spring trabaje bien)
@Builder // @Builder (Lombok) habilita el patrón "Builder" para crear objetos de forma fluida paso a paso
@Entity // Le dice a Hibernate que esta clase es una tabla de base de datos
@Table(name = "prestamos") // Nombra la tabla explícitamente como "prestamos"
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El ID del usuario es obligatorio")
    @Column(nullable = false)
    private Integer usuarioId;

    @NotNull(message = "El ID del libro es obligatorio")
    @Column(nullable = false)
    private Integer libroId;

    @NotNull(message = "La fecha de préstamo es obligatoria")
    @Column(nullable = false)
    private LocalDate fechaPrestamo;

    @NotNull(message = "La fecha de devolución es obligatoria")
    @Column(nullable = false)
    private LocalDate fechaDevolucion;

    @Enumerated(EnumType.STRING) // Obliga a Hibernate a guardar el texto literal del Enum (ej. "ACTIVO") en la base de datos, en lugar de su posición numérica (0, 1), evitando que la data se corrompa si agregamos nuevos estados
    @Column(nullable = false)
    private Estado_Prestamo estado;

}
