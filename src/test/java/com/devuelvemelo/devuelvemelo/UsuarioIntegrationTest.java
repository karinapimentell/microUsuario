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
        Usuario nuevo = new Usuario();
        nuevo.setRut("88888888-8");
        nuevo.setNombre("Marta Gomez");
        nuevo.setEmail("marta@test.com");

        // 1. Probar Creación
        String resCrear = usuarioService.crearUsuarioConFoto(nuevo, null);
        assertEquals("Usuario creado con éxito", resCrear);

        // 2. Probar que existe en BD
        assertTrue(usuarioRepository.findByRut("88888888-8").isPresent());

        // 3. Probar Eliminación
        String resEliminar = usuarioService.eliminarUsuario("88888888-8");
        assertEquals("Usuario eliminado del sistema", resEliminar);
    }
}