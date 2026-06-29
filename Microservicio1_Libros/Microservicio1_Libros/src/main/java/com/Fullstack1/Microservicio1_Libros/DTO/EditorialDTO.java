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
public class EditorialDTO extends RepresentationModel<EditorialDTO> {
    private Integer id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String correoContacto;
}
