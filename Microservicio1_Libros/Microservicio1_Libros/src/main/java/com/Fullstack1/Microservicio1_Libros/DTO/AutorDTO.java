package com.Fullstack1.Microservicio1_Libros.DTO;

import org.springframework.hateoas.RepresentationModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AutorDTO extends RepresentationModel<AutorDTO> {
    
    private Integer id;
    private String rut;
    private String correo;
    private String nombre;
    private String numero;
    private String ubicacion;
    private String nacionalidad;
}
