package com.foro.alura.dto.respuesta;

import com.foro.alura.modelo.Respuesta;
import com.foro.alura.modelo.Topico;
import com.foro.alura.modelo.Usuario;

import java.time.LocalDateTime;

public record DatosObtenerRespuesta(
        Long id,
        String autor,
        String mensaje,
        LocalDateTime fechaCreacion,
        Boolean solucion
) {
    public DatosObtenerRespuesta(Respuesta respuesta){
        this(
                respuesta.getId(),
                respuesta.getAutor().getNombre(),
                respuesta.getMensaje(),
                respuesta.getFechaCreacion(),
                respuesta.getSolucion()
        );
    }

}
