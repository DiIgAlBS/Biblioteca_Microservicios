package com.Fullstack1.Microservicio3_Prestamos.Assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.Fullstack1.Microservicio3_Prestamos.Controller.PrestamoController;
import com.Fullstack1.Microservicio3_Prestamos.DTO.PrestamoDTO;

@Component
public class PrestamoModelAssembler implements RepresentationModelAssembler<PrestamoDTO, EntityModel<PrestamoDTO>> {

    @Override
    public EntityModel<PrestamoDTO> toModel(PrestamoDTO prestamo) {
        return EntityModel.of(prestamo,
            linkTo(methodOn(PrestamoController.class).buscarPorId(prestamo.getId())).withSelfRel(),
            linkTo(methodOn(PrestamoController.class).listar()).withRel("prestamos")
        );
    }
}
