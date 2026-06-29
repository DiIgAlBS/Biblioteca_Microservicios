package com.Fullstack1.Microservicio1_Libros.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Es obligatorio ingresar el nombre del libro")
    @Size(min = 1, max = 100, message = "El nombre debe tener entre 1 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "Es obligatorio la fecha de publicación del libro")
    @Size(min = 1, max = 30, message = "La fecha debe tener entre 1 y 30 caracteres")
    @Column(nullable = false, length = 30)
    private String fechaPublicacion;

    @Column(name = "idioma_id")
    private Integer idiomaId;

    @Column(name = "autor_id")
    private Integer autorId;

    @Column(name = "editorial_id") 
    private Integer editorialId;

    @Column(name = "categoria_id")
    private Integer categoriaId;
}
