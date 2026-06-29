package com.Fullstack1.Microservicio1_Libros.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "editoriales")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Editorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre de la editorial es obligatorio")
    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(length = 200)
    private String direccion;

    @Column(length = 15)
    private String telefono; // Usamos String por seguridad de formato

    @Email(message = "Formato de correo inválido")
    @Column(unique = true)
    private String correoContacto;
}
