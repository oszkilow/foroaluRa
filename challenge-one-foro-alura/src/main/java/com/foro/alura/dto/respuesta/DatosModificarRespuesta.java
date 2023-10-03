package com.foro.alura.dto.respuesta;

import com.foro.alura.modelo.Respuesta;

public record DatosModificarRespuesta(
        Long id,
        String mensaje,
        Boolean solucion
) {
    public DatosModificarRespuesta(Respuesta respuesta) {
        this(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getSolucion()
        );
    }
}
