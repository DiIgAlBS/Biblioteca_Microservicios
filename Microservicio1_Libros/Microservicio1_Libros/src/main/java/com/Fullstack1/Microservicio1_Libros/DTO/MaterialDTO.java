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
public class MaterialDTO extends RepresentationModel<MaterialDTO> {
    
    private Integer id;
    private String nombreMaterial;
    private String tipoMaterial;
    private String estado;
    private Integer libroId;
}
