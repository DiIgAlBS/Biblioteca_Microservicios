package com.Fullstack1.Microservicio1_Libros.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventarioRequestDTO {
    
    @NotBlank(message = "Es obligatorio ingresar el servicio de control de productos")
    @Size(min = 1, max = 50, message = "El servicio debe tener entre 1 y 50 caracteres")
    @Pattern(regexp = "^[a-záéíóúñA-ZÁÉÍÓÚÑ0-9\\s\\-_]+$", message = "El servicio solo puede contener letras, números, espacios, guiones y guiones bajos")
    private String servicioControlProductos;

    @NotBlank(message = "Es obligatorio ingresar el servicio de compras a proveedores")
    @Size(min = 1, max = 50, message = "El servicio debe tener entre 1 y 50 caracteres")
    @Pattern(regexp = "^[a-záéíóúñA-ZÁÉÍÓÚÑ0-9\\s\\-_]+$", message = "El servicio solo puede contener letras, números, espacios, guiones y guiones bajos")
    private String servicioComprasProveedores;

    @NotBlank(message = "Es obligatorio ingresar el servicio de ventas y pedidos")
    @Size(min = 1, max = 50, message = "El servicio debe tener entre 1 y 50 caracteres")
    @Pattern(regexp = "^[a-záéíóúñA-ZÁÉÍÓÚÑ0-9\\s\\-_]+$", message = "El servicio solo puede contener letras, números, espacios, guiones y guiones bajos")
    private String servicioVentasPedidos;

    @NotBlank(message = "Es obligatorio ingresar el servicio de stock en almacén")
    @Size(min = 1, max = 50, message = "El servicio debe tener entre 1 y 50 caracteres")
    @Pattern(regexp = "^[a-záéíóúñA-ZÁÉÍÓÚÑ0-9\\s\\-_]+$", message = "El servicio solo puede contener letras, números, espacios, guiones y guiones bajos")
    private String servicioStockAlmacen;
}
