package com.Fullstack1.Microservicio2_Usuarios.Model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name = "notificaciones")
@Data 
@Builder 
@AllArgsConstructor 
@NoArgsConstructor 
public class Notificacion {  

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer id;

    @Column(nullable = false) 
    private Integer usuarioId;

    @Column(nullable = false, length = 500)
    private String mensaje;

    @Column(nullable = false)
    private LocalDate fechaEnvio;

    @Builder.Default
    @Column(nullable = false)
    private Boolean leida = false; 

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoNotificacion tipo;
}
