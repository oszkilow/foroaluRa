package com.foro.alura.service;

import com.foro.alura.constantes.Constantes;
import com.foro.alura.dto.usuario.Login;
import com.foro.alura.dto.usuario.TokenResponse;
import com.foro.alura.dto.usuario.UsuarioRequest;
import com.foro.alura.dto.usuario.UsuarioResponse;
import com.foro.alura.modelo.Usuario;
import com.foro.alura.repository.UserRepository;
import com.foro.alura.util.ForoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidaService validaService;

    @Override
    public ResponseEntity<String> signUp(UsuarioRequest usuarioRequest) {
        log.info("Registro interno de un usuario {}", usuarioRequest);
        try {
                Optional <Usuario> usuario = userRepository.findByEmail(usuarioRequest.getEmail());
                if (usuario.isEmpty()) {
                    userRepository.save(getUSerFromMap(usuarioRequest));
                    return ForoUtils.getResponseEntity("El usuario se registro con exito", HttpStatus.CREATED);
                } else {
                    return ForoUtils.getResponseEntity("El usuario con ese email ya existe", HttpStatus.BAD_REQUEST);
                }
        } catch (Exception e) {
            log.error("Ocurrio un error {}" , e.getMessage());
        }
        return ForoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Usuario getUSerFromMap(UsuarioRequest usuarioRequest) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioRequest.getNombre());
        usuario.setEmail(usuarioRequest.getEmail());
        usuario.setContrasena(usuarioRequest.getContrasena());
        return usuario;
    }


    @Override
    public TokenResponse autentica(Login login) {
        TokenResponse tokenResponse = new TokenResponse();
        userRepository.findByNombre(login.getNombreUsuario()).ifPresentOrElse(u -> {
                if (login.getPassword().equals(u.getContrasena())) {
                    String token = validaService.generaToken(u);
                    tokenResponse.setToken(token);
                }
        }, () -> log.error("ERROR"));
        return tokenResponse;
    }

    @Override
    public ResponseEntity<String> deleteUser(Integer id) {
        userRepository.deleteById(Long.valueOf(id));
        return ForoUtils.getResponseEntity("El usuario se elimino con exito", HttpStatus.OK);
    }

    @Override
    public UsuarioResponse updateUser(UsuarioRequest usuarioRequest) {
        UsuarioResponse actualiza = new UsuarioResponse();
        try {

            Optional<Usuario> respuesta = userRepository.findById(usuarioRequest.getId());
            if(respuesta.isPresent()){
                    Usuario usu = respuesta.get();
                    usu.setEmail(usuarioRequest.getEmail());
                    usu.setNombre(usuarioRequest.getNombre());
                    usu.setContrasena(usuarioRequest.getContrasena());
                    Usuario ul2 = userRepository.save(usu);
                    log.info("los datos se actualizaron " + ul2);
                    actualiza.setContrasena(ul2.getContrasena());
                    actualiza.setEmail(ul2.getEmail());
                    actualiza.setNombre(ul2.getNombre());
                    actualiza.setId(ul2.getId());
            }
        }catch(Exception e){
            log.error("Error al actualizar la informacion: "+e.getMessage(), e);
        }
        return actualiza;
    }

    @Override
    public List<UsuarioResponse> getAllUsers() {
        List<Usuario> usuarioList = userRepository.findAll();
        return usuarioList.stream().map(usuario -> {
            UsuarioResponse usuarioResponse = new UsuarioResponse();
            usuarioResponse.setId(usuario.getId());
            usuarioResponse.setNombre(usuario.getNombre());
            usuarioResponse.setEmail(usuario.getEmail());
            return usuarioResponse;
        }).toList();
    }

}