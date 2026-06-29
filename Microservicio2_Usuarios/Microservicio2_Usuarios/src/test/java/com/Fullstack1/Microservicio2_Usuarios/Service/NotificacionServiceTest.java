package com.Fullstack1.Microservicio2_Usuarios.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Fullstack1.Microservicio2_Usuarios.DTO.NotificacionDTO;
import com.Fullstack1.Microservicio2_Usuarios.DTO.NotificacionRequestDTO;
import com.Fullstack1.Microservicio2_Usuarios.Model.Notificacion;
import com.Fullstack1.Microservicio2_Usuarios.Model.TipoNotificacion;
import com.Fullstack1.Microservicio2_Usuarios.Repository.NotificacionRepository;

@ExtendWith(MockitoExtension.class)
class NotificacionServiceTest {

    @Mock
    private NotificacionRepository notificacionRepository;

    @InjectMocks
    private NotificacionService notificacionService;

    @Test
    void obtenerTodas_DebeRetornarListaDeNotificaciones() {
        Notificacion noti1 = Notificacion.builder().id(1).mensaje("Bienvenido").usuarioId(1).build();
        Notificacion noti2 = Notificacion.builder().id(2).mensaje("Alerta de cobro").usuarioId(1).build();
        Mockito.when(notificacionRepository.findAll()).thenReturn(List.of(noti1, noti2));
        List<NotificacionDTO> resultado = notificacionService.obtenerTodas();
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Bienvenido", resultado.get(0).getMensaje());
        Mockito.verify(notificacionRepository, Mockito.times(1)).findAll();
    }

    @Test
    void buscarPorId_CuandoExiste_DebeRetornarDTO() {
        Notificacion notificacionSimulada = Notificacion.builder()
            .id(1)
            .mensaje("Mensaje de prueba")
            .usuarioId(2)
            .build();
        Mockito.when(notificacionRepository.findById(1)).thenReturn(Optional.of(notificacionSimulada));
        NotificacionDTO resultado = notificacionService.buscarPorId(1);
        assertNotNull(resultado);
        assertEquals("Mensaje de prueba", resultado.getMensaje());
        Mockito.verify(notificacionRepository, Mockito.times(1)).findById(1);
    }

    @Test
    void buscarPorId_CuandoNoExiste_DebeLanzarExcepcion() {
        Mockito.when(notificacionRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> {
            notificacionService.buscarPorId(99);
        });
        Mockito.verify(notificacionRepository, Mockito.times(1)).findById(99);
    }

    @Test
    void obtenerPorUsuarioId_DebeRetornarListaFiltrada() {
        Notificacion noti = Notificacion.builder().id(1).mensaje("Notificación de Diego").usuarioId(5).build();
        Mockito.when(notificacionRepository.findByUsuarioId(5)).thenReturn(List.of(noti));
        List<NotificacionDTO> resultado = notificacionService.obtenerPorUsuarioId(5);
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(5, resultado.get(0).getUsuarioId());
        Mockito.verify(notificacionRepository, Mockito.times(1)).findByUsuarioId(5);
    }

    @Test
    void crearNotificacion_DebeCrearNotificacionYRetornarDTO() {
        NotificacionRequestDTO request = new NotificacionRequestDTO();
        request.setUsuarioId(3);
        request.setMensaje("Nueva alerta");
        request.setTipo(TipoNotificacion.ALERTA);
        Notificacion notificacionGuardada = Notificacion.builder()
            .id(1)
            .usuarioId(3)
            .mensaje("Nueva alerta")
            .fechaEnvio(LocalDate.now())
            .tipo(TipoNotificacion.ALERTA)
            .leida(false)
            .build();
        Mockito.when(notificacionRepository.save(any(Notificacion.class))).thenReturn(notificacionGuardada);
        NotificacionDTO resultado = notificacionService.crearNotificacion(request);
        assertNotNull(resultado, "El DTO resultante no debe ser nulo");
        assertEquals("Nueva alerta", resultado.getMensaje());
        assertEquals(3, resultado.getUsuarioId());
        assertFalse(resultado.getLeida());
        Mockito.verify(notificacionRepository, Mockito.times(1)).save(any(Notificacion.class));
    }

    @Test
    void actualizar_CuandoExiste_DebeModificarYRetornarDTO() {
        // Arrange
        Notificacion notificacionExistente = Notificacion.builder()
            .id(1)
            .usuarioId(3)
            .mensaje("Mensaje viejo")
            .tipo(TipoNotificacion.ALERTA)
            .build();
        NotificacionRequestDTO request = new NotificacionRequestDTO();
        request.setMensaje("Mensaje nuevo");
        request.setTipo(TipoNotificacion.ALERTA);
        Notificacion notificacionActualizada = Notificacion.builder()
            .id(1)
            .usuarioId(3)
            .mensaje("Mensaje nuevo")
            .tipo(TipoNotificacion.ALERTA)
            .build();
        Mockito.when(notificacionRepository.findById(1)).thenReturn(Optional.of(notificacionExistente));
        Mockito.when(notificacionRepository.save(any(Notificacion.class))).thenReturn(notificacionActualizada);
        NotificacionDTO resultado = notificacionService.actualizar(1, request);
        assertNotNull(resultado);
        assertEquals("Mensaje nuevo", resultado.getMensaje());
        Mockito.verify(notificacionRepository, Mockito.times(1)).findById(1);
        Mockito.verify(notificacionRepository, Mockito.times(1)).save(any(Notificacion.class));
    }

    @Test
    void eliminar_CuandoExiste_DebeLlamarAlRepositorio() {
        Mockito.when(notificacionRepository.existsById(1)).thenReturn(true);
        Mockito.doNothing().when(notificacionRepository).deleteById(1);
        notificacionService.eliminar(1);
        Mockito.verify(notificacionRepository, Mockito.times(1)).existsById(1);
        Mockito.verify(notificacionRepository, Mockito.times(1)).deleteById(1);
    }
}
