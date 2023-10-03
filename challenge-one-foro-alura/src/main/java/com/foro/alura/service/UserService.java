package com.foro.alura.service;

import com.foro.alura.dto.usuario.Login;
import com.foro.alura.dto.usuario.TokenResponse;
import com.foro.alura.dto.usuario.UsuarioRequest;
import com.foro.alura.dto.usuario.UsuarioResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    ResponseEntity<String> signUp(UsuarioRequest usuarioRequest);

    TokenResponse autentica(Login login);

    ResponseEntity<String> deleteUser(Integer id);

    UsuarioResponse updateUser(UsuarioRequest usuarioRequest);

    List<UsuarioResponse> getAllUsers ();


}
