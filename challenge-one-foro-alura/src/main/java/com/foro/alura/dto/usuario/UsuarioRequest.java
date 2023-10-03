package com.foro.alura.dto.usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequest {
    private Long id;
    private String nombre;
    private String email;
    private String contrasena;
}
