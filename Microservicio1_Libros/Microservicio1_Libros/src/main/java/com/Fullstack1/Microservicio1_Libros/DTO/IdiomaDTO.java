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
public class IdiomaDTO extends RepresentationModel<IdiomaDTO> {
    
    private Integer id;
    private String nombreIdioma;
}
