package com.foro.alura.controller;

import com.foro.alura.constantes.Constantes;
import com.foro.alura.dto.usuario.Login;
import com.foro.alura.dto.usuario.TokenResponse;
import com.foro.alura.dto.usuario.UsuarioRequest;
import com.foro.alura.dto.usuario.UsuarioResponse;
import com.foro.alura.service.UserService;
import com.foro.alura.service.ValidaService;
import com.foro.alura.util.ForoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ValidaService validaService;

    @PostMapping(value = "signup",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registrarUsuario(@RequestBody UsuarioRequest usuarioRequest, @RequestParam(value = "token") String token) {
        UsuarioResponse usuarioResponse = null;
        try {
            usuarioResponse = validaService.decodeJWTToken(token);
            if (usuarioResponse == null) {
                return ForoUtils.getResponseEntity("Error token", HttpStatus.NOT_ACCEPTABLE);
            }
            return userService.signUp(usuarioRequest);
        } catch (Exception e) {
            log.error("Ocurrio un error {}", e.getMessage());
        }
        return ForoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(value = "login")
    public ResponseEntity<TokenResponse> login(@RequestBody Login loginDTO) {
        TokenResponse tokenGenericResponse = userService.autentica(loginDTO);
        return new ResponseEntity<>(tokenGenericResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "delete",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> eliminarUsuario(@RequestParam(value = "token") String token, @RequestParam(value = "id") Integer id) {
        try {
            UsuarioResponse usuarioResponse = validaService.decodeJWTToken(token);
            if (usuarioResponse == null) {
                return ForoUtils.getResponseEntity("Error token", HttpStatus.NOT_ACCEPTABLE);
            } else {
                return userService.deleteUser(id);
            }
        } catch (Exception e) {
            log.error("Ocurrio un error {}", e.getMessage());
        }
        return ForoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @PutMapping(value = "updateUser",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity actualizaUsuario(@RequestBody UsuarioRequest usuarioRequest, @RequestParam(value = "token") String token) {
        try {
            UsuarioResponse usuarioResponse = validaService.decodeJWTToken(token);
            if (usuarioResponse == null) {
                return ForoUtils.getResponseEntity("Error token", HttpStatus.NOT_ACCEPTABLE);
            } else {
                UsuarioResponse usuarioResponse1 = userService.updateUser(usuarioRequest);
                return new ResponseEntity<>(usuarioResponse1, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Ocurrio un error {}", e.getMessage());
        }
        return ForoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "getAllUser",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity obtieneAllUsuario(@RequestParam(value = "token") String token) {
        try {
            UsuarioResponse usuarioResponse = validaService.decodeJWTToken(token);
            if (usuarioResponse == null) {
                return ForoUtils.getResponseEntity("Error token", HttpStatus.NOT_ACCEPTABLE);
            } else {
                List<UsuarioResponse> responseList =  userService.getAllUsers();
                return new ResponseEntity<>(responseList, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Ocurrio un error {}", e.getMessage());
        }
        return ForoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

