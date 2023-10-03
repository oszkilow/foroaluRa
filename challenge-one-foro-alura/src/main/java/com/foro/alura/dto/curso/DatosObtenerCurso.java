package com.foro.alura.dto.curso;

import com.foro.alura.modelo.Curso;

public record DatosObtenerCurso(
        Long id,
        String nombre,
        String categoria
) {
    public DatosObtenerCurso(Curso curso){
        this(
                curso.getId(),
                curso.getNombre(),
                curso.getCategoria()
        );
    }
}
