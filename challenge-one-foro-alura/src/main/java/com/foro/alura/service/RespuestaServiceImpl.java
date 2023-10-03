package com.foro.alura.service;

import com.foro.alura.dto.respuesta.DatosModificarRespuesta;
import com.foro.alura.dto.respuesta.DatosObtenerRespuesta;
import com.foro.alura.dto.respuesta.DatosRegistroRespuesta;
import com.foro.alura.modelo.Respuesta;
import com.foro.alura.modelo.Topico;
import com.foro.alura.modelo.Usuario;
import com.foro.alura.repository.RespuestaRepository;
import com.foro.alura.repository.TopicoRepository;
import com.foro.alura.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class RespuestaServiceImpl implements RespuestaService{
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Override
    public DatosObtenerRespuesta obtenerRespuesta(Long id) {
        var respuesta = respuestaRepository.findById(id).get();

        return new DatosObtenerRespuesta(respuesta);    }

    @Override
    public Respuesta crearRespuesta(DatosRegistroRespuesta datosRegistroRespuesta) {
        Respuesta respuesta = new Respuesta(datosRegistroRespuesta);

        Optional<Usuario> autor = usuarioRepository.findById(datosRegistroRespuesta.autor_id());
        if (autor.isPresent()) {
            respuesta.setAutor(autor.get());
        } else {
            log.error("EL usuario no existe en la BD");
        }
        Optional<Topico> topico = topicoRepository.findById(datosRegistroRespuesta.topico_id());
        if (topico.isPresent()) {
            respuesta.setTopico(topico.get());
        } else {
            log.error("EL topico no existe en la BD");
        }
        Respuesta respuesta1 = null;
        try {
            respuesta1 = respuestaRepository.save(respuesta);
        } catch (Exception e) {
            log.error("ERROR {}" , e.getMessage());
            return null;
        }
        return respuesta1;
    }

    @Override
    public DatosModificarRespuesta modificarRespuesta(DatosModificarRespuesta datosModificarRespuesta) {
        var respuesta = respuestaRepository.getReferenceById(datosModificarRespuesta.id());

        respuesta.modificar(datosModificarRespuesta);

        return new DatosModificarRespuesta(respuesta);
    }

    @Override
    public void eliminarRespuesta(Long id) {
        var respuesta = respuestaRepository.findById(id);

        if (respuesta.isPresent()) {
            Respuesta respuestaAEliminar = respuesta.get();
            respuestaRepository.delete(respuestaAEliminar);
        } else {
            throw new EntityNotFoundException(String.format("la respuesta con el id %d no fue encontrado", id));
        }
    }
}
