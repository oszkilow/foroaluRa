package com.foro.alura.dto.topico;

public record DatosRegistroTopico(
        String titulo,
        String mensaje,
        Long autor_id,
        Long curso_id
) {
}
