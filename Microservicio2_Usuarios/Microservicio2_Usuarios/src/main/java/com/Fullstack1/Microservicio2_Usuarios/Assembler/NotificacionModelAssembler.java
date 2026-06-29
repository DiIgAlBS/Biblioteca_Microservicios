package com.Fullstack1.Microservicio2_Usuarios.Assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.Fullstack1.Microservicio2_Usuarios.Controller.NotificacionController;
import com.Fullstack1.Microservicio2_Usuarios.DTO.NotificacionDTO;

@Component
public class NotificacionModelAssembler implements RepresentationModelAssembler<NotificacionDTO, NotificacionDTO> {

    @Override
    public NotificacionDTO toModel(NotificacionDTO dto) {
        dto.add(linkTo(methodOn(NotificacionController.class).buscarPorId(dto.getId())).withSelfRel());
        dto.add(linkTo(methodOn(NotificacionController.class).listar()).withRel("todas-las-notificaciones"));
        dto.add(linkTo(methodOn(NotificacionController.class).notificacionesPorUsuario(dto.getUsuarioId())).withRel("notificaciones-del-usuario"));
        return dto;
    }
}
