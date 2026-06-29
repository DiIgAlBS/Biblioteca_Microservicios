package com.Fullstack1.Microservicio2_Usuarios.Service;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Fullstack1.Microservicio2_Usuarios.DTO.UsuarioDTO;
import com.Fullstack1.Microservicio2_Usuarios.DTO.UsuarioRequestDTO;
import com.Fullstack1.Microservicio2_Usuarios.Model.Usuario;
import com.Fullstack1.Microservicio2_Usuarios.Repository.UsuarioRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void guardar_DebeCrearUsuarioYRetornarDTO() {
        // 1. Arrange (Preparar los datos)
        UsuarioRequestDTO request = new UsuarioRequestDTO();
        request.setNombre("Diego");
        request.setCorreo("diego@test.com");
        request.setNumero("123456789");
        Usuario usuarioSimulado = Usuario.builder()
            .id(1)
            .nombre("Diego")
            .correo("diego@test.com")
            .numero("123456789")
            .estadoNotificaciones("ACTIVO")
            .build();
        Mockito.when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioSimulado);
        UsuarioDTO resultado = usuarioService.guardar(request);
        assertNotNull(resultado, "El DTO resultante no debe ser nulo");
        assertEquals("Diego", resultado.getNombre());
        assertEquals("ACTIVO", resultado.getEstadoNotificaciones());
        Mockito.verify(usuarioRepository, Mockito.times(1)).save(any(Usuario.class));
    }

    @Test
    void buscarPorId_CuandoExiste_DebeRetornarUsuarioDTO() {
        Usuario usuarioSimulado = Usuario.builder()
            .id(1)
            .nombre("Juan")
            .correo("juan@test.com")
            .build();      
        Mockito.when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuarioSimulado));
        UsuarioDTO resultado = usuarioService.buscarPorId(1);
        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        Mockito.verify(usuarioRepository, Mockito.times(1)).findById(1);
    }

    @Test
    void obtenerTodos_DebeRetornarListaDeUsuarios() {
        Usuario usuario1 = Usuario.builder().id(1).nombre("Diego").correo("diego@test.com").build();
        Usuario usuario2 = Usuario.builder().id(2).nombre("Juan").correo("juan@test.com").build();
        Mockito.when(usuarioRepository.findAll()).thenReturn(java.util.List.of(usuario1, usuario2));
        java.util.List<UsuarioDTO> resultado = usuarioService.obtenerTodos();
        assertNotNull(resultado);
        assertEquals(2, resultado.size(), "La lista debe contener exactamente 2 usuarios");
        assertEquals("Diego", resultado.get(0).getNombre());
        Mockito.verify(usuarioRepository, Mockito.times(1)).findAll();
    }

    @Test
    void eliminar_CuandoUsuarioExiste_DebeLlamarAlRepositorio() {
        Usuario usuarioSimulado = Usuario.builder().id(1).nombre("Diego").build();
        Mockito.when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuarioSimulado));
        Mockito.doNothing().when(usuarioRepository).delete(any(Usuario.class));
        usuarioService.eliminar(1);
        Mockito.verify(usuarioRepository, Mockito.times(1)).findById(1);
        Mockito.verify(usuarioRepository, Mockito.times(1)).delete(any(Usuario.class));
    }

    @Test
    void buscarPorId_CuandoNoExiste_DebeLanzarExcepcion() {
        Mockito.when(usuarioRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> {
            usuarioService.buscarPorId(99);
        }, "Debe lanzar una excepción si el usuario no existe"); 
        Mockito.verify(usuarioRepository, Mockito.times(1)).findById(99);
    }
}
