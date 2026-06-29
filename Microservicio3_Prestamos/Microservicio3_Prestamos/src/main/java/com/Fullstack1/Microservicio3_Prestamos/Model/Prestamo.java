package com.Fullstack1.Microservicio3_Prestamos.Model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
@Builder 
@Entity 
@Table(name = "prestamos")
@Schema(description = "Entidad que representa el préstamo de un libro de la biblioteca a un usuario específico")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID autoincremental único del préstamo en la base de datos", example = "1")
    private Integer id;

    @Column(name = "usuario_id", nullable = false)
    @Schema(description = "ID del usuario que solicita el préstamo (Microservicio de Usuarios)", example = "10")
    private Integer usuarioId;

    @Column(name = "libro_id", nullable = false)
    @Schema(description = "ID del libro prestado (Microservicio de Libros)", example = "45")
    private Integer libroId;

    @Column(name = "fecha_prestamo", nullable = false)
    @Schema(description = "Fecha exacta en la que se realiza la entrega del libro")
    private LocalDate fechaPrestamo;

    @Column(name = "fecha_devolucion", nullable = false)
    @Schema(description = "Fecha límite establecida para que el usuario retorne el libro")
    private LocalDate fechaDevolucion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    @Schema(description = "Estado actual del ciclo del préstamo (ACTIVO, DEVUELTO, ATRASADO)")
    private EstadoPrestamo estado;
}
