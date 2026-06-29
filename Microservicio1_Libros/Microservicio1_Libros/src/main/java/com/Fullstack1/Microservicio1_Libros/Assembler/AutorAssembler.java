package com.Fullstack1.Microservicio1_Libros.Assembler;

import com.Fullstack1.Microservicio1_Libros.Controller.AutorController;
import com.Fullstack1.Microservicio1_Libros.DTO.AutorDTO;
import com.Fullstack1.Microservicio1_Libros.Model.Autor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@Slf4j
@RequiredArgsConstructor
public class AutorAssembler extends RepresentationModelAssemblerSupport<Autor, AutorDTO> {
    
    public AutorAssembler() {
        super(AutorController.class, AutorDTO.class);
    }
    
    @Override
    public AutorDTO toModel(Autor entity) {
        log.debug("Assembler: Convirtiendo Autor a DTO - ID: {}", entity.getId());
        
        AutorDTO dto = AutorDTO.builder()
                .id(entity.getId())
                .rut(entity.getRut())
                .correo(entity.getCorreo())
                .nombre(entity.getNombre())
                .numero(entity.getNumero())
                .ubicacion(entity.getUbicacion())
                .nacionalidad(entity.getNacionalidad())
                .build();
        
        // Agregar links HATEOAS
        try {
            // Link self (obtener este autor)
            dto.add(linkTo(methodOn(AutorController.class)
                    .obtenerPorId(entity.getId()))
                    .withSelfRel());
            
            // Link a todos los autores
            dto.add(linkTo(methodOn(AutorController.class)
                    .obtenerTodos())
                    .withRel("autores"));
            
            // Link para actualizar
            dto.add(linkTo(methodOn(AutorController.class)
                    .actualizar(entity.getId(), null))
                    .withRel("actualizar"));
            
            // Link para eliminar
            dto.add(linkTo(methodOn(AutorController.class)
                    .eliminar(entity.getId()))
                    .withRel("eliminar"));
            
            log.debug("Assembler: Links HATEOAS agregados para autor ID: {}", entity.getId());
        } catch (Exception e) {
            log.error("Assembler: Error al agregar links HATEOAS", e);
        }
        
        return dto;
    }
}
