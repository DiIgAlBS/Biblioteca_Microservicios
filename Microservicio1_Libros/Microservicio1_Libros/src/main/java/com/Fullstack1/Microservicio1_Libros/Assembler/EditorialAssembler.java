package com.Fullstack1.Microservicio1_Libros.Assembler;

import com.Fullstack1.Microservicio1_Libros.Controller.EditorialController;
import com.Fullstack1.Microservicio1_Libros.DTO.EditorialDTO;
import com.Fullstack1.Microservicio1_Libros.Model.Editorial;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@Slf4j
@RequiredArgsConstructor
public class EditorialAssembler extends RepresentationModelAssemblerSupport<Editorial, EditorialDTO> {
    
    public EditorialAssembler() {
        super(EditorialController.class, EditorialDTO.class);
    }
    
    @Override
    public EditorialDTO toModel(Editorial entity) {
        log.debug("Assembler: Convirtiendo Editorial a DTO - ID: {}", entity.getId());
        
        EditorialDTO dto = EditorialDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .direccion(entity.getDireccion())
                .telefono(entity.getTelefono())
                .correoContacto(entity.getCorreoContacto())
                .build();
        
   
        try {
       
            dto.add(linkTo(methodOn(EditorialController.class)
                    .obtenerPorId(entity.getId()))
                    .withSelfRel());
            
         
            dto.add(linkTo(methodOn(EditorialController.class)
                    .obtenerTodos())
                    .withRel("editoriales"));
            
       
            dto.add(linkTo(methodOn(EditorialController.class)
                    .actualizar(entity.getId(), null))
                    .withRel("actualizar"));
            
         
            dto.add(linkTo(methodOn(EditorialController.class)
                    .eliminar(entity.getId()))
                    .withRel("eliminar"));
            
            log.debug("Assembler: Links HATEOAS agregados para editorial ID: {}", entity.getId());
        } catch (Exception e) {
            log.error("Assembler: Error al agregar links HATEOAS", e);
        }
        
        return dto;
    }
}
