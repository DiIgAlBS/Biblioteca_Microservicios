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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Le dice a Hibernate que esta clase es una tabla de base de datos
@Table(name = "multas") // Nombra explícitamente la tabla en la base de datos como "multas"
@Data // @Data (Lombok) genera automáticamente por debajo los Getters, Setters, toString y constructores
@Builder // @Builder (Lombok) habilita el patrón "Builder" para crear objetos de forma fluida paso a paso
@AllArgsConstructor // @AllArgsConstructor (Lombok): Crea un constructor con todos los atributos de la clase
@NoArgsConstructor // @NoArgsConstructor (Lombok): Crea un constructor vacío (obligatorio para que Spring trabaje bien)
public class Multa {

    @Id // Define este campo como la Llave Primaria (Primary Key) de la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Le dice a la base de datos (ej. MySQL) que use Auto-Increment (1, 2, 3...)
    private Integer id;

    @Column(nullable = false) // Crea la columna y le pone la restricción NOT NULL a nivel de base de datos
    private Integer prestamoId; 
    
    @Column(nullable = false)
    private Integer usuarioId;

    @Column(nullable = false)
    private Double monto;
    
    @Column(nullable = false)
    private String motivo; 
    
    @Column(name = "fecha_generada")
    private LocalDate fechaGeneracion;
    
    private LocalDate fechaPago; // Esta columna sí puede ser nula, porque al crear la multa aún no se ha pagado

    @Enumerated(EnumType.STRING) // Obliga a Hibernate a guardar el texto del Enum ("PENDIENTE") y no un número (0)
    @Column(nullable = false)
    private Estado_Multa estado;

}
