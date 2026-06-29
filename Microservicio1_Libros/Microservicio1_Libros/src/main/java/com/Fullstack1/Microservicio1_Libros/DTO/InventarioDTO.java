package com.Fullstack1.Microservicio1_Libros.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventarioDTO extends RepresentationModel<InventarioDTO> {
    
    private Integer id;
    private String servicioControlProductos;
    private String servicioComprasProveedores;
    private String servicioVentasPedidos;
    private String servicioStockAlmacen;
}
