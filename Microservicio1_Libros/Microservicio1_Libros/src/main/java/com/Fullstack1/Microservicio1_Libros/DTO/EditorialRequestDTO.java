package com.Fullstack1.Microservicio1_Libros.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditorialRequestDTO {
    
    @NotBlank(message = "El nombre de la editorial es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;
    
    @Size(max = 200, message = "La dirección no puede exceder 200 caracteres")
    private String direccion;
    
    @Pattern(regexp = "^(\\+?56)?\\s?([0-9]){8,10}$", message = "El teléfono debe ser válido (ej: +56912345678 o 912345678)")
    @Size(max = 15, message = "El teléfono no puede exceder 15 caracteres")
    private String telefono;
    
    @Email(message = "El correo debe tener un formato válido")
    private String correoContacto;
}
