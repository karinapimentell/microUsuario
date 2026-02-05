package com.devuelvemelo.devuelvemelo.controllers;

import com.devuelvemelo.devuelvemelo.models.entity.Usuario;
import com.devuelvemelo.devuelvemelo.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name= "Usuario", description = "Endpoints para la gestión de confianza y usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Listar todos los usuarios", description = "Devuelve una lista completa de usuarios registrados")
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }

    @Operation(summary = "Obtener un usuario por su RUT", description = "Busca un usuario específico usando su RUT")
    @GetMapping("/{rut}")
    public ResponseEntity<?> obtenerPorRut(@PathVariable String rut) {
        Usuario usuario = usuarioService.obtenerPorRut(rut);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
    }

    @Operation(summary = "Registrar nuevo usuario", description = "Crea un usuario validando que el RUT y Email no existan")
    @PostMapping("/registro")
    public ResponseEntity<String> registrar(@RequestBody Usuario usuario) {
        String respuesta = usuarioService.crearUsuario(usuario);
        if (respuesta.contains("Error")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @Operation(summary = "Actualizar datos de un usuario", description = "Modifica los datos de un usuario existente buscando por RUT")
    @PutMapping("/actualizar/{rut}")
    public ResponseEntity<String> actualizar(@PathVariable String rut, @RequestBody Usuario usuario) {
        String respuesta = usuarioService.actualizarUsuario(rut, usuario);
        if (respuesta.contains("no encontrado")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Eliminar un usuario", description = "Borra definitivamente a un usuario del sistema")
    @DeleteMapping("/eliminar/{rut}")
    public ResponseEntity<String> eliminar(@PathVariable String rut) {
        String respuesta = usuarioService.eliminarUsuario(rut);
        if (respuesta.contains("no encontrado")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Registrar usuario con foto", description = "Permite subir una imagen desde el dispositivo")
    @PostMapping(value = "/registro", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> registrar(
    @RequestPart("usuario") Usuario usuario, 
    @RequestPart(value = "foto", required = false) MultipartFile foto) {
    
        String respuesta = usuarioService.crearUsuarioConFoto(usuario, foto);
    
        if (respuesta.contains("Error")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }
}