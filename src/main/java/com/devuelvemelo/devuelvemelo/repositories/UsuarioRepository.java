package com.devuelvemelo.devuelvemelo.repositories;

import com.devuelvemelo.devuelvemelo.models.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByRut(String rut);
    
    // Cambiado de findByEmailUsuario a findByEmail
    Optional<Usuario> findByEmail(String email);

    // Cambiado de findByRutAndEmailUsuario a findByRutAndEmail
    Optional<Usuario> findByRutAndEmail(String rut, String email);

    boolean existsByRut(String rut);
    
    boolean existsByEmail(String email);
}
   

