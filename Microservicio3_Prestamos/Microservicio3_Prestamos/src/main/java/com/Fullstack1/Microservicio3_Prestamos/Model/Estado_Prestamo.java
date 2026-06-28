package com.Fullstack1.Microservicio3_Prestamos.Model;

public enum Estado_Prestamo {// Define el ciclo de vida estricto (los únicos estados posibles) de un préstamo
    ACTIVO, // El libro está en manos del usuario y todavía está a tiempo de devolverlo
    DEVUELTO, // El ciclo terminó con éxito: el usuario entregó el libro
    ATRASADO // El ciclo falló: el usuario aún tiene el libro y ya se pasó de la fecha límite
}
