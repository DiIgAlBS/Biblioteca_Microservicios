package com.Fullstack1.Microservicio1_Libros.DTO;

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
public class IdiomaRequestDTO {
    
    @NotBlank(message = "Es obligatorio ingresar el nombre del idioma")
    @Size(min = 1, max = 50, message = "El nombre del idioma debe tener entre 1 y 50 caracteres")
    @Pattern(regexp = "^[a-záéíóúñA-ZÁÉÍÓÚÑ\\s]+$", message = "El nombre del idioma solo puede contener letras y espacios")
    private String nombreIdioma;
}
