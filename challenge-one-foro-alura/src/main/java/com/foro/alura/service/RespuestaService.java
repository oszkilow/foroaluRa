package com.foro.alura.service;

import com.foro.alura.dto.respuesta.DatosModificarRespuesta;
import com.foro.alura.dto.respuesta.DatosObtenerRespuesta;
import com.foro.alura.dto.respuesta.DatosRegistroRespuesta;
import com.foro.alura.modelo.Respuesta;

public interface RespuestaService {

    DatosObtenerRespuesta obtenerRespuesta(Long id);

    Respuesta crearRespuesta(DatosRegistroRespuesta datosRegistroRespuesta);

    DatosModificarRespuesta modificarRespuesta(DatosModificarRespuesta datosModificarRespuesta);

    void eliminarRespuesta (Long id);
}
