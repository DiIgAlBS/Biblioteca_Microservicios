package com.Fullstack1.Microservicio1_Libros.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutorRequestDTO {

    @Size(max = 12, message = "El RUT no puede superar los 12 caracteres")
    private String rut;

    @Email(message = "El formato del correo electrónico no es válido")
    private String correo;

    @NotBlank(message = "El nombre del autor es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;

    @Size(max = 15, message = "El número telefónico no puede superar los 15 caracteres")
    private String numero;

    @Size(max = 200, message = "La ubicación no puede superar los 200 caracteres")
    private String ubicacion;

    @Size(max = 100, message = "La nacionalidad no puede superar los 100 caracteres")
    private String nacionalidad;
}
