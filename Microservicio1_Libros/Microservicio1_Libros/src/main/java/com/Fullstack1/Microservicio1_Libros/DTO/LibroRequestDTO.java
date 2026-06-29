package com.Fullstack1.Microservicio1_Libros.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class LibroRequestDTO {
    
    @NotBlank(message = "Es obligatorio ingresar el nombre del libro")
    @Size(min = 1, max = 100, message = "El nombre debe tener entre 1 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "Es obligatorio ingresar la fecha de publicación del libro")
    @Pattern(regexp = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$", message = "La fecha debe estar en formato yyyy-MM-dd (ej: 2024-06-28)")
    private String fechaPublicacion;

    @NotNull(message = "Es obligatorio ingresar el ID del idioma")
    private Integer idiomaId;

    @NotNull(message = "Es obligatorio ingresar el ID del autor")
    private Integer autorId;

    @NotNull(message = "Es obligatorio ingresar el ID de la editorial")
    private Integer editorialId;

    @NotNull(message = "Es obligatorio ingresar el ID de la categoría")
    private Integer categoriaId;
}
