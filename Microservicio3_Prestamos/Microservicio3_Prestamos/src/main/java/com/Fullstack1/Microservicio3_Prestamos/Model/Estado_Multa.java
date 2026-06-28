package com.Fullstack1.Microservicio3_Prestamos.Model;

public enum Estado_Multa {// "enum" crea un tipo de dato especial que limita las opciones a una lista estricta
    PENDIENTE, // Estado inicial automático cuando el sistema detecta un atraso
    PAGADA, // Estado cuando el usuario ya ha cancelado su deuda económica
    ANULADA // Estado excepcional por si el bibliotecario necesita perdonar o borrar la multa por un error
}
