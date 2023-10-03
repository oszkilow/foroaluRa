package com.foro.alura.service;

import com.foro.alura.dto.usuario.UsuarioResponse;
import com.foro.alura.modelo.Usuario;


public interface ValidaService {


    String generaToken(Usuario usuario);

    UsuarioResponse decodeJWTToken(String token);


}
