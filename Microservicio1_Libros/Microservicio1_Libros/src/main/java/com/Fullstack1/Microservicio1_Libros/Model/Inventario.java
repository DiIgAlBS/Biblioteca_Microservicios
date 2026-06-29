package com.Fullstack1.Microservicio1_Libros.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder 
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inventarios")
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Es obligatorio ingresar el servicio que desea gestionar")
    @Size(min = 1, max = 50, message = "El servicio debe tener entre 1 y 50 caracteres")
    @Column(nullable = false, length = 50)
    private String servicioControlProductos;

    @NotBlank(message = "Es obligatorio ingresar el servicio que desea gestionar")
    @Size(min = 1, max = 50, message = "El servicio debe tener entre 1 y 50 caracteres")
    @Column(nullable = false, length = 50)
    private String servicioComprasProveedores;

    @NotBlank(message = "Es obligatorio ingresar el servicio que desea gestionar")
    @Size(min = 1, max = 50, message = "El servicio debe tener entre 1 y 50 caracteres")
    @Column(nullable = false, length = 50)
    private String servicioVentasPedidos;

    @NotBlank(message = "Es obligatorio ingresar el servicio que desea gestionar")
    @Size(min = 1, max = 50, message = "El servicio debe tener entre 1 y 50 caracteres")
    @Column(nullable = false, length = 50)
    private String servicioStockAlmacen;
}
