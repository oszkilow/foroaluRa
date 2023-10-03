package com.foro.alura.controller;

import com.foro.alura.constantes.Constantes;
import com.foro.alura.dto.topico.DatosModificarTopico;
import com.foro.alura.dto.topico.DatosObtenerTopico;
import com.foro.alura.dto.topico.DatosObtenerTopicos;
import com.foro.alura.dto.topico.DatosRegistroTopico;
import com.foro.alura.dto.usuario.UsuarioResponse;
import com.foro.alura.modelo.Topico;
import com.foro.alura.service.TopicoService;
import com.foro.alura.service.ValidaService;
import com.foro.alura.util.ForoUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;
    @Autowired
    private ValidaService validaService;
    @PostMapping(value = "addTopico",  produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity RegistrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico,@RequestParam(value = "token") String token) {
        Topico topicoCreado = new Topico();
        try {
            UsuarioResponse usuarioResponse = validaService.decodeJWTToken(token);
            if (usuarioResponse == null) {
                return ForoUtils.getResponseEntity("Error token", HttpStatus.NOT_ACCEPTABLE);
            } else {
                topicoCreado = topicoService.registrarTopico(datosRegistroTopico);
                return new ResponseEntity<>(topicoCreado, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Ocurrio un error {}", e.getMessage());
        }
        return ForoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "getTopicos",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity obtenerTopicos (@RequestParam(value = "token") String token) {
        try {
            UsuarioResponse usuarioResponse = validaService.decodeJWTToken(token);
            if (usuarioResponse == null) {
                return ForoUtils.getResponseEntity("Error token", HttpStatus.NOT_ACCEPTABLE);
            } else {
                List<DatosObtenerTopicos> list = topicoService.obtenerTopicos();
                return new ResponseEntity<>(list, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Ocurrio un error {}", e.getMessage());
        }
        return ForoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{id}")
    public ResponseEntity obtenerTopicoPorId(@PathVariable Long id, @RequestParam(value = "token") String token) {
        try {
            UsuarioResponse usuarioResponse = validaService.decodeJWTToken(token);
            if (usuarioResponse == null) {
                return ForoUtils.getResponseEntity("Error token", HttpStatus.NOT_ACCEPTABLE);
            } else {
                DatosObtenerTopico topico = topicoService.obtenerTopico(id);
                return new ResponseEntity<>(topico, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Ocurrio un error {}", e.getMessage());
        }
        return ForoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(value = "updateTopicos",  produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity modificarTopico(@RequestBody @Valid DatosModificarTopico datosModificarTopico,@RequestParam(value = "token") String token) {
        try {
            UsuarioResponse usuarioResponse = validaService.decodeJWTToken(token);
            if (usuarioResponse == null) {
                return ForoUtils.getResponseEntity("Error token", HttpStatus.NOT_ACCEPTABLE);
            } else {
                DatosModificarTopico datosModificarTopico1 = topicoService.modificarTopico(datosModificarTopico);
                return new ResponseEntity<>(datosModificarTopico1, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Ocurrio un error {}", e.getMessage());
        }
        return ForoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico (@PathVariable Long id, @RequestParam(value = "token") String token){
        try {
            UsuarioResponse usuarioResponse = validaService.decodeJWTToken(token);
            if (usuarioResponse == null) {
                return ForoUtils.getResponseEntity("Error token", HttpStatus.NOT_ACCEPTABLE);
            } else {
                topicoService.eliminarTopico(id);
                return ForoUtils.getResponseEntity("El topicc se elimino con exito", HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Ocurrio un error {}", e.getMessage());
        }
        return ForoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
