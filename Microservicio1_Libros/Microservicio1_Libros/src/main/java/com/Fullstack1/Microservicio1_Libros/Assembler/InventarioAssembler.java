package com.Fullstack1.Microservicio1_Libros.Assembler;

import com.Fullstack1.Microservicio1_Libros.Controller.InventarioController;
import com.Fullstack1.Microservicio1_Libros.DTO.InventarioDTO;
import com.Fullstack1.Microservicio1_Libros.Model.Inventario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@Slf4j
@RequiredArgsConstructor
public class InventarioAssembler extends RepresentationModelAssemblerSupport<Inventario, InventarioDTO> {
    
    public InventarioAssembler() {
        super(InventarioController.class, InventarioDTO.class);
    }
    
    @Override
    public InventarioDTO toModel(Inventario entity) {
        log.debug("Assembler: Convirtiendo Inventario a DTO - ID: {}", entity.getId());
        
        InventarioDTO dto = InventarioDTO.builder()
                .id(entity.getId())
                .servicioControlProductos(entity.getServicioControlProductos())
                .servicioComprasProveedores(entity.getServicioComprasProveedores())
                .servicioVentasPedidos(entity.getServicioVentasPedidos())
                .servicioStockAlmacen(entity.getServicioStockAlmacen())
                .build();
        
        // Agregar links HATEOAS
        try {
            // Link self (obtener este inventario)
            dto.add(linkTo(methodOn(InventarioController.class)
                    .obtenerPorId(entity.getId()))
                    .withSelfRel());
            
            // Link a todos los inventarios
            dto.add(linkTo(methodOn(InventarioController.class)
                    .obtenerTodos())
                    .withRel("inventarios"));
            
            // Link para actualizar
            dto.add(linkTo(methodOn(InventarioController.class)
                    .actualizar(entity.getId(), null))
                    .withRel("actualizar"));
            
            // Link para eliminar
            dto.add(linkTo(methodOn(InventarioController.class)
                    .eliminar(entity.getId()))
                    .withRel("eliminar"));
            
            log.debug("Assembler: Links HATEOAS agregados para inventario ID: {}", entity.getId());
        } catch (Exception e) {
            log.error("Assembler: Error al agregar links HATEOAS", e);
        }
        
        return dto;
    }
}
