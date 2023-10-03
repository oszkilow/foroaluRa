package com.foro.alura.controller;

import com.foro.alura.constantes.Constantes;
import com.foro.alura.dto.respuesta.DatosModificarRespuesta;
import com.foro.alura.dto.respuesta.DatosObtenerRespuesta;
import com.foro.alura.dto.respuesta.DatosRegistroRespuesta;
import com.foro.alura.dto.usuario.UsuarioResponse;
import com.foro.alura.modelo.Respuesta;
import com.foro.alura.service.RespuestaService;
import com.foro.alura.service.TopicoService;
import com.foro.alura.service.ValidaService;
import com.foro.alura.util.ForoUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("topicos/respuestas")
public class RespuestaController {

    @Autowired
    private TopicoService topicoService;
    @Autowired
    private ValidaService validaService;
    @Autowired
    private RespuestaService respuestaService;

    @GetMapping("/{id}")
    public ResponseEntity obtenerRespuesta(@PathVariable(value = "id")  Long id, @RequestParam(value = "token") String token) {
        try {
            UsuarioResponse usuarioResponse = validaService.decodeJWTToken(token);
            if (usuarioResponse == null) {
                return ForoUtils.getResponseEntity("Error token", HttpStatus.NOT_ACCEPTABLE);
            } else {
                DatosObtenerRespuesta respuesta = respuestaService.obtenerRespuesta(id);
                return new ResponseEntity<>(respuesta, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Ocurrio un error {}", e.getMessage());
        }
        return ForoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping(value = "addResponse",  produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity crearRespuesta( @RequestBody DatosRegistroRespuesta datosRegistroRespuesta,  @RequestParam(value = "token") String token) {
        try {
            UsuarioResponse usuarioResponse = validaService.decodeJWTToken(token);
            if (usuarioResponse == null) {
                return ForoUtils.getResponseEntity("Error token", HttpStatus.NOT_ACCEPTABLE);
            } else {
                Respuesta respuesta = respuestaService.crearRespuesta(datosRegistroRespuesta);
                return new ResponseEntity<>(respuesta, HttpStatus.OK);
            }
        } catch (Exception e) {
            return ForoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "updateResponse",  produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity modificarRespuesta(@RequestBody DatosModificarRespuesta datosModificarRespuesta,  @RequestParam(value = "token") String token){
        try {
            UsuarioResponse usuarioResponse = validaService.decodeJWTToken(token);
            if (usuarioResponse == null) {
                return ForoUtils.getResponseEntity("Error token", HttpStatus.NOT_ACCEPTABLE);
            } else {
                DatosModificarRespuesta datosModificarRespuesta1 = respuestaService.modificarRespuesta(datosModificarRespuesta);
                return new ResponseEntity<>(datosModificarRespuesta1, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Ocurrio un error {}", e.getMessage());
        }
        return ForoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarRespuesta(@PathVariable Long id, @RequestParam(value = "token") String token){
        try {
            UsuarioResponse usuarioResponse = validaService.decodeJWTToken(token);
            if (usuarioResponse == null) {
                return ForoUtils.getResponseEntity("Error token", HttpStatus.NOT_ACCEPTABLE);
            } else {
                respuestaService.eliminarRespuesta(id);
                return ForoUtils.getResponseEntity("La respuesta se elimino con exito", HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Ocurrio un error {}", e.getMessage());
        }
        return ForoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
