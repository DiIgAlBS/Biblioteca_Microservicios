package com.Fullstack1.Microservicio3_Prestamos.Assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.Fullstack1.Microservicio3_Prestamos.Controller.MultaController;
import com.Fullstack1.Microservicio3_Prestamos.DTO.MultaDTO;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MultaModelAssembler implements RepresentationModelAssembler<MultaDTO, EntityModel<MultaDTO>> {

    @Override
    public EntityModel<MultaDTO> toModel(MultaDTO multa) {
        return EntityModel.of(multa,
            linkTo(methodOn(MultaController.class).buscarPorId(multa.getId())).withSelfRel(),
            linkTo(methodOn(MultaController.class).listar()).withRel("multas")
        );
    }

}
