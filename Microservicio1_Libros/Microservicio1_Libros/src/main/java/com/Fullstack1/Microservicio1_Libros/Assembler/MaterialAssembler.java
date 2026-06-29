package com.Fullstack1.Microservicio1_Libros.Assembler;

import com.Fullstack1.Microservicio1_Libros.Controller.MaterialController;
import com.Fullstack1.Microservicio1_Libros.DTO.MaterialDTO;
import com.Fullstack1.Microservicio1_Libros.Model.Material;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@Slf4j
@RequiredArgsConstructor
public class MaterialAssembler extends RepresentationModelAssemblerSupport<Material, MaterialDTO> {
    
    public MaterialAssembler() {
        super(MaterialController.class, MaterialDTO.class);
    }
    
    @Override
    public MaterialDTO toModel(Material entity) {
        log.debug("Assembler: Convirtiendo Material a DTO - ID: {}", entity.getId());
        
        MaterialDTO dto = MaterialDTO.builder()
                .id(entity.getId())
                .nombreMaterial(entity.getNombreMaterial())
                .tipoMaterial(entity.getTipoMaterial())
                .estado(entity.getEstado())
                .libroId(entity.getLibroId())
                .build();
        
        
        try {
    
            dto.add(linkTo(methodOn(MaterialController.class)
                    .obtenerPorId(entity.getId()))
                    .withSelfRel());
            
     
            dto.add(linkTo(methodOn(MaterialController.class)
                    .obtenerTodos())
                    .withRel("materiales"));
            
     
            if (entity.getLibroId() != null) {
                dto.add(linkTo(methodOn(MaterialController.class)
                        .obtenerPorLibro(entity.getLibroId()))
                        .withRel("materiales-libro"));
            }
            
      
            if (entity.getEstado() != null) {
                dto.add(linkTo(methodOn(MaterialController.class)
                        .obtenerPorEstado(entity.getEstado()))
                        .withRel("materiales-estado"));
            }
            
       
            dto.add(linkTo(methodOn(MaterialController.class)
                    .actualizar(entity.getId(), null))
                    .withRel("actualizar"));
            
     
            dto.add(linkTo(methodOn(MaterialController.class)
                    .eliminar(entity.getId()))
                    .withRel("eliminar"));
            
            log.debug("Assembler: Links HATEOAS agregados para material ID: {}", entity.getId());
        } catch (Exception e) {
            log.error("Assembler: Error al agregar links HATEOAS", e);
        }
        
        return dto;
    }
}
