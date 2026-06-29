package com.Fullstack1.Microservicio3_Prestamos.Model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Estados posibles para el control de multas de un usuario")
public enum EstadoMulta {
    @Schema(description = "Estado inicial automático cuando el sistema detecta un atraso")
    PENDIENTE, 
    @Schema(description = "Estado cuando el usuario ya ha cancelado su deuda")
    PAGADA, 
    @Schema(description = "Estado excepcional por si el bibliotecario necesita condonar o borrar la multa por un error administrativo")
    ANULADA
}
