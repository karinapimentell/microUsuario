package com.devuelvemelo.devuelvemelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.devuelvemelo.devuelvemelo.models.entity.Usuario;
import com.devuelvemelo.devuelvemelo.repositories.UsuarioRepository;
import com.devuelvemelo.devuelvemelo.services.UsuarioService;

import java.util.Optional;

public class TestUsuario {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario();
        usuario.setRut("12345678-9");
        usuario.setNombre("Juan Pérez");
        usuario.setEmail("juan.perez@example.com");
        usuario.setPassword("1234");
    }

    @Test
    public void testCrearUsuario_Exitoso() {

        // Simular que no existe ni RUT ni email
        when(usuarioRepository.findByRut(usuario.getRut()))
                .thenReturn(Optional.empty());

        when(usuarioRepository.findByEmail(usuario.getEmail()))
                .thenReturn(Optional.empty());

        when(usuarioRepository.save(any(Usuario.class)))
                .thenReturn(usuario);

        String resultado = usuarioService.crearUsuario(usuario);

        assertEquals("Usuario registrado exitosamente en Devuélvemelo", resultado);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    public void testCrearUsuario_RutDuplicado() {

        when(usuarioRepository.findByRut(usuario.getRut()))
                .thenReturn(Optional.of(usuario));

        RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class,
                () -> usuarioService.crearUsuario(usuario)
        );

        assertEquals("El RUT ya se encuentra registrado", exception.getMessage());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    public void testEliminarUsuario_NoExistente() {

        when(usuarioRepository.findByRut(anyString()))
                .thenReturn(Optional.empty());

        String resultado = usuarioService.eliminarUsuario("1-1");

        assertEquals("No se pudo eliminar: Usuario no encontrado", resultado);
    }
}