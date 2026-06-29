package com.Fullstack1.Microservicio1_Libros.Assembler;

import com.Fullstack1.Microservicio1_Libros.Controller.LibroController;
import com.Fullstack1.Microservicio1_Libros.DTO.LibroDTO;
import com.Fullstack1.Microservicio1_Libros.Model.Libro;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@Slf4j
@RequiredArgsConstructor
public class LibroAssembler extends RepresentationModelAssemblerSupport<Libro, LibroDTO> {
    
    public LibroAssembler() {
        super(LibroController.class, LibroDTO.class);
    }
    
    @Override
    public LibroDTO toModel(Libro entity) {
        log.debug("Assembler: Convirtiendo Libro a DTO - ID: {}", entity.getId());
        
        LibroDTO dto = LibroDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .fechaPublicacion(entity.getFechaPublicacion())
                .idiomaId(entity.getIdiomaId())
                .autorId(entity.getAutorId())
                .editorialId(entity.getEditorialId())
                .categoriaId(entity.getCategoriaId())
                .build();
        
        // Agregar links HATEOAS
        try {
            // Link self (obtener este libro)
            dto.add(linkTo(methodOn(LibroController.class)
                    .obtenerPorId(entity.getId()))
                    .withSelfRel());
            
            // Link a todos los libros
            dto.add(linkTo(methodOn(LibroController.class)
                    .obtenerTodos())
                    .withRel("libros"));
            
            // Link a libros por autor
            if (entity.getAutorId() != null) {
                dto.add(linkTo(methodOn(LibroController.class)
                        .obtenerPorAutor(entity.getAutorId()))
                        .withRel("libros-autor"));
            }
            
            // Link a libros por editorial
            if (entity.getEditorialId() != null) {
                dto.add(linkTo(methodOn(LibroController.class)
                        .obtenerPorEditorial(entity.getEditorialId()))
                        .withRel("libros-editorial"));
            }
            
            // Link para actualizar
            dto.add(linkTo(methodOn(LibroController.class)
                    .actualizar(entity.getId(), null))
                    .withRel("actualizar"));
            
            // Link para eliminar
            dto.add(linkTo(methodOn(LibroController.class)
                    .eliminar(entity.getId()))
                    .withRel("eliminar"));
            
            log.debug("Assembler: Links HATEOAS agregados para libro ID: {}", entity.getId());
        } catch (Exception e) {
            log.error("Assembler: Error al agregar links HATEOAS", e);
        }
        
        return dto;
    }
}
