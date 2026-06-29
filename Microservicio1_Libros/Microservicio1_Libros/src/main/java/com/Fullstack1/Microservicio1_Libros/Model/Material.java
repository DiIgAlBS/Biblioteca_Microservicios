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
@Table(name = "materiales")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Es obligatorio ingresar el nombre del material")
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String nombreMaterial;

    @Column(name = "tipo_material")
    private String tipoMaterial;

    @Column(name = "estado")
    private String estado;

    @Column(name = "libro_id")
    private Integer libroId;
}
