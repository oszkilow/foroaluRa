package com.foro.alura.controller;

import com.foro.alura.constantes.Constantes;
import com.foro.alura.dto.curso.DatosObtenerCurso;
import com.foro.alura.dto.curso.DatosRegistroCurso;
import com.foro.alura.dto.topico.DatosObtenerTopicos;
import com.foro.alura.dto.usuario.UsuarioResponse;
import com.foro.alura.modelo.Curso;
import com.foro.alura.service.CursoService;
import com.foro.alura.service.ValidaService;
import com.foro.alura.util.ForoUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
@Slf4j
public class CursoController {

    @Autowired
    private CursoService cursoService;
    @Autowired
    private ValidaService validaService;
    @PostMapping(value = "addCurso",  produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity registrarCurso(@RequestBody DatosRegistroCurso datosRegistroCurso, @RequestParam(value = "token") String token) {
        try {
            UsuarioResponse usuarioResponse = validaService.decodeJWTToken(token);
            if (usuarioResponse == null) {
                return ForoUtils.getResponseEntity("Error token", HttpStatus.NOT_ACCEPTABLE);
            } else {
                Curso curso = cursoService.registraCurso(datosRegistroCurso);
                return new ResponseEntity<>(curso, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Ocurrio un error {}", e.getMessage());
        }
        return ForoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "getCursos",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity obtenerCursos(@RequestParam(value = "token") String token) {
        try {
            UsuarioResponse usuarioResponse = validaService.decodeJWTToken(token);
            if (usuarioResponse == null) {
                return ForoUtils.getResponseEntity("Error token", HttpStatus.NOT_ACCEPTABLE);
            } else {
                List<Curso> cursos = cursoService.obtenerCursos();
                return new ResponseEntity<>(cursos, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Ocurrio un error {}", e.getMessage());
        }
        return ForoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
