package com.foro.alura.dto.respuesta;

public record DatosRegistroRespuesta(
        Long topico_id,
        Long autor_id,
        String mensaje
        ) {
}
