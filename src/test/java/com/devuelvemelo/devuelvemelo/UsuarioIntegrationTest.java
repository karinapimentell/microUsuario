package com.devuelvemelo.devuelvemelo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.devuelvemelo.devuelvemelo.models.entity.Usuario;
import com.devuelvemelo.devuelvemelo.repositories.UsuarioRepository;
import com.devuelvemelo.devuelvemelo.services.UsuarioService;

@SpringBootTest
@Transactional 
public class UsuarioIntegrationTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void testFlujoCompletoUsuario() {
        Usuario nuevo = Usuario.builder()
        .rut("88888888-8")
        .nombre("Marta")
        .apellidos("Gomez")
        .telefonoCelular("987654321")
        .email("marta@test.com")
        .password("Password@123")
        .build();


        // Crear
        String resCrear = usuarioService.crearUsuarioConFoto(nuevo, null);
        assertEquals("Usuario creado con éxito", resCrear);

        // Validar guardado
        Usuario guardado = usuarioRepository.findByRut("88888888-8").orElseThrow();
        assertEquals("Marta", guardado.getNombre());

        // Eliminar
        String resEliminar = usuarioService.eliminarUsuario("88888888-8");
        assertEquals("Usuario eliminado del sistema", resEliminar); 
    }
}