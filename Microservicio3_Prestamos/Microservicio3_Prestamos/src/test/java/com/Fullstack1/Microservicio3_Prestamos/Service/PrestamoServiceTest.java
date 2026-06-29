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

import com.Fullstack1.Microservicio3_Prestamos.DTO.PrestamoDTO;
import com.Fullstack1.Microservicio3_Prestamos.DTO.PrestamoRequestDTO;
import com.Fullstack1.Microservicio3_Prestamos.Model.EstadoPrestamo;
import com.Fullstack1.Microservicio3_Prestamos.Model.Prestamo;
import com.Fullstack1.Microservicio3_Prestamos.Repository.PrestamoRepository;

@ExtendWith(MockitoExtension.class)
class PrestamoServiceTest {

    @Mock
    private PrestamoRepository prestamoRepository;

    @InjectMocks
    private PrestamoService prestamoService;

    private Prestamo prestamoBase;
    private PrestamoRequestDTO requestBase;

    @BeforeEach
    void setUp() {
        prestamoBase = Prestamo.builder()
            .id(1)
            .usuarioId(10)
            .libroId(20)
            .fechaPrestamo(LocalDate.now())
            .fechaDevolucion(LocalDate.now().plusDays(7))
            .estado(EstadoPrestamo.ACTIVO)
            .build();
        requestBase = new PrestamoRequestDTO();
        requestBase.setUsuarioId(10);
        requestBase.setLibroId(20);
        requestBase.setFechaPrestamo(LocalDate.now());
        requestBase.setFechaDevolucion(LocalDate.now().plusDays(7));
    }
    
    @Test
    @DisplayName("Debe retornar lista de préstamos exitosamente")
    void obtenerTodos_Exito() {
        when(prestamoRepository.findAll()).thenReturn(List.of(prestamoBase));
        List<PrestamoDTO> resultado = prestamoService.obtenerTodos();
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals(prestamoBase.getLibroId(), resultado.get(0).getLibroId());
        verify(prestamoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe retornar un préstamo cuando el ID existe")
    void buscarPorId_Exito() {
        when(prestamoRepository.findById(1)).thenReturn(Optional.of(prestamoBase));
        PrestamoDTO resultado = prestamoService.buscarPorId(1);
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el préstamo no existe")
    void buscarPorId_NoEncontrado() {
        when(prestamoRepository.findById(99)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            prestamoService.buscarPorId(99);
        });
        assertTrue(exception.getMessage().contains("Préstamo no encontrado"));
    }

    @Test
    @DisplayName("Debe crear un préstamo exitosamente")
    void crearPrestamo_Exito() {
        when(prestamoRepository.save(any(Prestamo.class))).thenReturn(prestamoBase);
        PrestamoDTO resultado = prestamoService.crearPrestamo(requestBase);
        assertNotNull(resultado);
        assertEquals(EstadoPrestamo.ACTIVO, resultado.getEstado());
        verify(prestamoRepository, times(1)).save(any(Prestamo.class));
    }

    @Test
    @DisplayName("Debe eliminar un préstamo si el ID existe")
    void eliminar_Exito() {
        when(prestamoRepository.existsById(1)).thenReturn(true);
        doNothing().when(prestamoRepository).deleteById(1);
        assertDoesNotThrow(() -> prestamoService.eliminar(1));
        verify(prestamoRepository, times(1)).deleteById(1);
    }

}
