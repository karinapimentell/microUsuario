package com.devuelvemelo.devuelvemelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
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
    }

    @Test
    public void testCrearUsuario_Exitoso() {
        // Simulamos que el RUT no existe
        when(usuarioRepository.existsByRut(usuario.getRut())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // Llamamos al método que tienes en tu Service (pasando null en la foto)
        String resultado = usuarioService.crearUsuarioConFoto(usuario, null);

        // Mensaje exacto de tu UsuarioService
        assertEquals("Usuario creado con éxito", resultado);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    public void testCrearUsuario_RutDuplicado() {
        // Simulamos que el RUT ya existe
        when(usuarioRepository.existsByRut(usuario.getRut())).thenReturn(true);

        String resultado = usuarioService.crearUsuarioConFoto(usuario, null);

        // Mensaje exacto de tu UsuarioService
        assertEquals("Error: RUT duplicado", resultado);
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    public void testEliminarUsuario_NoExistente() {
        when(usuarioRepository.findByRut(anyString())).thenReturn(Optional.empty());

        String resultado = usuarioService.eliminarUsuario("1-1");

        assertEquals("No se pudo eliminar: Usuario no encontrado", resultado);
    }
}