package com.Fullstack1.Microservicio3_Prestamos.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data // @Data genera automáticamente Getters, Setters y constructores básicos
public class MultaRequestDTO {

    @NotNull(message = "El ID del préstamo es obligatorio") // Verifica que el cliente (Postman/Frontend) no envíe el ID del préstamo como nulo/vacío
    private Integer prestamoId;
    
    @NotNull(message = "El ID del usuario es obligatorio") // Asegura que siempre sepamos a qué usuario asignarle la multa
    private Integer usuarioId;
    
    @NotNull(message = "El monto no puede estar vacío")
    @Positive(message = "El monto de la multa debe ser mayor a cero") // Doble regla: No puede ser nulo Y además tiene que ser un número mayor a cero
    private Double monto;
    
    @NotBlank(message = "Debe especificar un motivo para la multa") // @NotBlank es especial para textos: Verifica que no sea nulo Y que no manden puros espacios en blanco
    private String motivo;
}
