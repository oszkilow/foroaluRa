package com.foro.alura.service;

import com.foro.alura.dto.curso.DatosRegistroCurso;
import com.foro.alura.modelo.Curso;
import com.foro.alura.repository.CursoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CursoServiceImpl implements CursoService{
    @Autowired
    private CursoRepository cursoRepository;
    @Override
    public Curso registraCurso(DatosRegistroCurso datosRegistroCurso) {
        Curso curso = new Curso(datosRegistroCurso);
        return cursoRepository.save(curso);
    }

    @Override
    public List<Curso> obtenerCursos() {
        var cursos = cursoRepository.findAll();
        return cursos;
    }
}
