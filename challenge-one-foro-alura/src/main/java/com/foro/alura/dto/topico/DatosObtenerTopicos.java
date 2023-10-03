package com.foro.alura.dto.topico;

import com.foro.alura.dto.respuesta.DatosObtenerRespuesta;
import com.foro.alura.modelo.StatusTopico;
import com.foro.alura.modelo.Topico;

import java.time.LocalDateTime;
import java.util.List;

public record DatosObtenerTopicos(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fecha,
        StatusTopico statusTopico,
        String autor,
        String curso,
        List<DatosObtenerRespuesta> respuestas
) {
    public DatosObtenerTopicos(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                topico.getAutor().getNombre(),
                topico.getCurso().getNombre(),
                topico.getRespuestas().stream().map(DatosObtenerRespuesta::new).toList()
        );
    }


}
