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
public class MaterialRequestDTO {
    
    @NotBlank(message = "Es obligatorio ingresar el nombre del material")
    @Size(min = 1, max = 50, message = "El nombre debe tener entre 1 y 50 caracteres")
    @Pattern(
        regexp = "^[a-záéíóúñA-ZÁÉÍÓÚÑ\\s]+$",
        message = "El nombre del material solo puede contener letras y espacios"
    )
    private String nombreMaterial;

    @Size(max = 50, message = "El tipo de material no puede exceder 50 caracteres")
    @Pattern(
        regexp = "^[a-záéíóúñA-ZÁÉÍÓÚÑ\\s]*$",
        message = "El tipo de material solo puede contener letras y espacios"
    )
    private String tipoMaterial;

    @Pattern(
        regexp = "^(DISPONIBLE|NO_DISPONIBLE|EN_MANTENIMIENTO)?$",
        message = "El estado debe ser: DISPONIBLE, NO_DISPONIBLE o EN_MANTENIMIENTO"
    )
    private String estado;

    private Integer libroId;
}
