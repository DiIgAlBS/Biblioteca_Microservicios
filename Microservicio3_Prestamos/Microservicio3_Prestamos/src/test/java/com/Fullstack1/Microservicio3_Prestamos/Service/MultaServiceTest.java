package com.Fullstack1.Microservicio3_Prestamos.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import com.Fullstack1.Microservicio3_Prestamos.DTO.MultaDTO;
import com.Fullstack1.Microservicio3_Prestamos.DTO.MultaRequestDTO;
import com.Fullstack1.Microservicio3_Prestamos.Model.EstadoMulta;
import com.Fullstack1.Microservicio3_Prestamos.Model.Multa;
import com.Fullstack1.Microservicio3_Prestamos.Repository.MultaRepository;

@ExtendWith(MockitoExtension.class)
class MultaServiceTest {

    @Mock
    private MultaRepository multaRepository;

    @InjectMocks
    private MultaService multaService;

    private Multa multaBase;
    private MultaRequestDTO requestBase;

    @BeforeEach
    void setUp() {
        multaBase = Multa.builder()
            .id(1)
            .usuarioId(10)
            .prestamoId(5)
            .monto(15.50)
            .fechaGeneracion(LocalDate.now())
            .estado(EstadoMulta.PENDIENTE)
            .build();
        requestBase = new MultaRequestDTO();
        requestBase.setUsuarioId(10);
        requestBase.setPrestamoId(5);
        requestBase.setMonto(15.50);
    }

    @Test
    @DisplayName("Debe retornar lista de multas exitosamente")
    void obtenerTodas_Exito() {
        when(multaRepository.findAll()).thenReturn(List.of(multaBase));
        List<MultaDTO> resultado = multaService.obtenerTodas();
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals(multaBase.getMonto(), resultado.get(0).getMonto());
        verify(multaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe retornar una multa cuando el ID existe")
    void buscarPorId_Exito() {
        when(multaRepository.findById(1)).thenReturn(Optional.of(multaBase));
        MultaDTO resultado = multaService.buscarPorId(1);
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals(multaBase.getUsuarioId(), resultado.getUsuarioId());
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando la multa no existe")
    void buscarPorId_NoEncontrado() {
        when(multaRepository.findById(99)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            multaService.buscarPorId(99);
        });
        assertTrue(exception.getMessage().toLowerCase().contains("no encontrad"));
    }

    @Test
    @DisplayName("Debe crear una multa exitosamente")
    void crearMulta_Exito() {
        when(multaRepository.save(any(Multa.class))).thenReturn(multaBase);
        MultaDTO resultado = multaService.crearMulta(requestBase);
        assertNotNull(resultado);
        assertEquals(EstadoMulta.PENDIENTE, resultado.getEstado()); 
        verify(multaRepository, times(1)).save(any(Multa.class));
    }

    @Test
    @DisplayName("Debe eliminar una multa si el ID existe")
    void eliminar_Exito() {
        when(multaRepository.existsById(1)).thenReturn(true);
        doNothing().when(multaRepository).deleteById(1);
        assertDoesNotThrow(() -> multaService.eliminar(1));
        verify(multaRepository, times(1)).deleteById(1);
    }
}
