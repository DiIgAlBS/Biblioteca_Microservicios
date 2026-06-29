package com.Fullstack1.Microservicio1_Libros.Assembler;

import com.Fullstack1.Microservicio1_Libros.Controller.IdiomaController;
import com.Fullstack1.Microservicio1_Libros.DTO.IdiomaDTO;
import com.Fullstack1.Microservicio1_Libros.Model.Idioma;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@Slf4j
@RequiredArgsConstructor
public class IdiomaAssembler extends RepresentationModelAssemblerSupport<Idioma, IdiomaDTO> {
    
    public IdiomaAssembler() {
        super(IdiomaController.class, IdiomaDTO.class);
    }
    
    @Override
    public IdiomaDTO toModel(Idioma entity) {
        log.debug("Assembler: Convirtiendo Idioma a DTO - ID: {}", entity.getId());
        
        IdiomaDTO dto = IdiomaDTO.builder()
                .id(entity.getId())
                .nombreIdioma(entity.getNombreIdioma())
                .build();
        
   
        try {
   
            dto.add(linkTo(methodOn(IdiomaController.class)
                    .obtenerPorId(entity.getId()))
                    .withSelfRel());
            
      
            dto.add(linkTo(methodOn(IdiomaController.class)
                    .obtenerTodos())
                    .withRel("idiomas"));
            
         
            dto.add(linkTo(methodOn(IdiomaController.class)
                    .actualizar(entity.getId(), null))
                    .withRel("actualizar"));
            
      
            dto.add(linkTo(methodOn(IdiomaController.class)
                    .eliminar(entity.getId()))
                    .withRel("eliminar"));
            
            log.debug("Assembler: Links HATEOAS agregados para idioma ID: {}", entity.getId());
        } catch (Exception e) {
            log.error("Assembler: Error al agregar links HATEOAS", e);
        }
        
        return dto;
    }
}
