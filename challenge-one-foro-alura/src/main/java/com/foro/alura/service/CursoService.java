package com.foro.alura.service;

import com.foro.alura.dto.curso.DatosRegistroCurso;
import com.foro.alura.modelo.Curso;

import java.util.List;

public interface CursoService {
    Curso registraCurso(DatosRegistroCurso datosRegistroCurso);
    List<Curso> obtenerCursos();
}
