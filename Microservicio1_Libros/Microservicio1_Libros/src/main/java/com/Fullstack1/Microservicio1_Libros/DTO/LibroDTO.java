package com.Fullstack1.Microservicio1_Libros.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LibroDTO extends RepresentationModel<LibroDTO> {
    
    private Integer id;
    private String nombre;
    private String fechaPublicacion;
    private Integer idiomaId;
    private Integer autorId;
    private Integer editorialId;
    private Integer categoriaId;
}
