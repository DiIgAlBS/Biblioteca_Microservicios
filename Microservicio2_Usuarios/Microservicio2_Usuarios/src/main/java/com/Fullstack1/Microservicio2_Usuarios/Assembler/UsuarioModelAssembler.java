package com.Fullstack1.Microservicio2_Usuarios.Assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.Fullstack1.Microservicio2_Usuarios.Controller.NotificacionController;
import com.Fullstack1.Microservicio2_Usuarios.Controller.UsuarioController;
import com.Fullstack1.Microservicio2_Usuarios.DTO.UsuarioDTO;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<UsuarioDTO, UsuarioDTO> {

    @Override
    public UsuarioDTO toModel(UsuarioDTO dto) {
        dto.add(linkTo(methodOn(UsuarioController.class).buscarPorId(dto.getId())).withSelfRel());
        dto.add(linkTo(methodOn(UsuarioController.class).todosLosUsuarios()).withRel("todos-los-usuarios"));
        dto.add(linkTo(methodOn(NotificacionController.class).notificacionesPorUsuario(dto.getId())).withRel("notificaciones-del-usuario"));
        return dto;
    }
}
