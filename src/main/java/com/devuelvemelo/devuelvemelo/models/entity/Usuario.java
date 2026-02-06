package com.devuelvemelo.devuelvemelo.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotNull(message = "El RUT es obligatorio")
    private String rut;

    @NotNull(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String nombre;

    @NotNull(message = "Los apellidos son obligatorios")
    @Size(max = 50, message = "Los apellidos no pueden tener más de 50 caracteres")
    private String apellidos;

    private String codPais;
    
    @NotNull(message = "El teléfono celular es obligatorio")
    @Size(min = 8, max = 15, message = "El teléfono celular debe tener entre 8 y 15 dígitos")
    private String telefonoCelular;

    @Column(unique = true, nullable = false)
    @NotNull(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    @Size(max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "El email debe tener un formato válido")
    private String email;

    @NotNull(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 20, message = "La contraseña debe tener entre 8 y 20 caracteres") // Aumentado a 20 por seguridad
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", 
             message = "La contraseña debe contener al menos un número, una letra mayúscula, una minúscula y un carácter especial")
    private String password;

    // Se guardará el nombre o link de la foto
    private String imagenUrl;
}