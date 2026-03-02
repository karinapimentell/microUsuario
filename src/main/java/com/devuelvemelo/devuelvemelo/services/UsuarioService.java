package com.devuelvemelo.devuelvemelo.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import com.devuelvemelo.devuelvemelo.models.entity.Usuario;
import com.devuelvemelo.devuelvemelo.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public String crearUsuario(Usuario usuario) {

        if (usuario == null) 
            throw new RuntimeException("Datos de usuario no proporcionados");

        if (usuarioRepository.findByRut(usuario.getRut()).isPresent()) {
            throw new RuntimeException("El RUT ya se encuentra registrado");
        }

        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está en uso");
        }

        usuarioRepository.save(usuario);

        return "Usuario registrado exitosamente en Devuélvemelo";
    }



    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerPorRut(String rut) {
        return usuarioRepository.findByRut(rut).orElse(null);
    }

    @Transactional
    public String actualizarUsuario(String rut, Usuario datosNuevos) {
        return usuarioRepository.findByRut(rut).map(existente -> {
            existente.setNombre(datosNuevos.getNombre());
            existente.setApellidos(datosNuevos.getApellidos());
            existente.setTelefonoCelular(datosNuevos.getTelefonoCelular());
            existente.setEmail(datosNuevos.getEmail());
            existente.setPassword(datosNuevos.getPassword());
            usuarioRepository.save(existente);
            return "Datos actualizados correctamente";
        }).orElse("Usuario no encontrado con el RUT: " + rut);
    }

    @Transactional
    public String eliminarUsuario(String rut) {
        return usuarioRepository.findByRut(rut).map(u -> {
            usuarioRepository.delete(u);
            return "Usuario eliminado del sistema";
        }).orElse("No se pudo eliminar: Usuario no encontrado");
    }

    
}