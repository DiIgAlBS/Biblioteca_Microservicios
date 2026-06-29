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

@Entity
@Table(name = "multas") 
@Data 
@Builder 
@AllArgsConstructor 
@NoArgsConstructor 
@Schema(description = "Entidad que representa una sanción económica aplicada a un usuario por el retraso en la devolución de un libro")
public class Multa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Schema(description = "ID autoincremental único de la multa en la base de datos", example = "1")
    private Integer id;

    @Column(nullable = false) 
    @Schema(description = "ID del préstamo que originó la multa (perteneciente a este mismo microservicio)", example = "142")
    private Integer prestamoId; 
    
    @Column(nullable = false)
    @Schema(description = "ID del usuario penalizado (proveniente del Microservicio de Usuarios)", example = "5")
    private Integer usuarioId;

    @Column(nullable = false)
    @Schema(description = "Monto económico acumulado de la multa", example = "2500.00")
    private Double monto;
    
    @Column(nullable = false)
    @Schema(description = "Razón detallada de la emisión de la multa", example = "Retraso de 3 días en la devolución del libro 'Clean Code'")
    private String motivo; 
    
    @Column(name = "fecha_generada")
    @Schema(description = "Fecha exacta en la que el sistema emitió la multa automáticamente")
    private LocalDate fechaGeneracion;
    
    @Column(name = "fecha_pago")
    @Schema(description = "Fecha en la que el usuario liquidó la deuda. Permanecerá nula si el estado es PENDIENTE")
    private LocalDate fechaPago; 

    @Enumerated(EnumType.STRING) 
    @Column(nullable = false)
    @Schema(description = "Estado actual del ciclo de vida de la multa")
    private EstadoMulta estado;
}
