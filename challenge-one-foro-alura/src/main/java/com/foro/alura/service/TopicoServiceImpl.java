package com.foro.alura.service;

import com.foro.alura.dto.respuesta.DatosObtenerRespuesta;
import com.foro.alura.dto.topico.DatosModificarTopico;
import com.foro.alura.dto.topico.DatosObtenerTopico;
import com.foro.alura.dto.topico.DatosObtenerTopicos;
import com.foro.alura.dto.topico.DatosRegistroTopico;
import com.foro.alura.modelo.Curso;
import com.foro.alura.modelo.Respuesta;
import com.foro.alura.modelo.Topico;
import com.foro.alura.modelo.Usuario;
import com.foro.alura.repository.CursoRepository;
import com.foro.alura.repository.RespuestaRepository;
import com.foro.alura.repository.TopicoRepository;
import com.foro.alura.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TopicoServiceImpl implements TopicoService{
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UserRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private RespuestaRepository respuestaRepository;


    @Override
    public Topico registrarTopico(DatosRegistroTopico datosRegistroTopico) {
        if (topicoRepository.existsByTituloOrMensaje(datosRegistroTopico.titulo(), datosRegistroTopico.mensaje())){
            throw new ValidationException("no se topicos duplicados");
        }
        Topico topico = new Topico(datosRegistroTopico);
        Optional<Usuario> usuario = usuarioRepository.findById(datosRegistroTopico.autor_id());
        if (usuario.isPresent()) {
            topico.setAutor(usuario.get());
        } else {
            log.error("EL usuario no existe en la BD");
        }
        Optional<Curso> curso = cursoRepository.findById(datosRegistroTopico.curso_id());
        if (curso.isPresent()) {
            topico.setCurso(curso.get());
        } else {
            log.error("EL curso no existe en la BD");
        }
        return topicoRepository.save(topico);
    }

    @Override
    public List<DatosObtenerTopicos> obtenerTopicos() {
        List<Topico> listTopicos = topicoRepository.findAll();
        DatosObtenerTopicos datosObtenerTopicos;
        List<DatosObtenerRespuesta> datosObtenerRespuestaList = new ArrayList<>();
        List<DatosObtenerTopicos> datosObtenerTopicosList = new ArrayList<>();
        for (Topico list : listTopicos) {
            for (Respuesta listRes : list.getRespuestas()) {
                DatosObtenerRespuesta datosObtenerRespuesta = new DatosObtenerRespuesta(
                        listRes.getId(),
                        listRes.getAutor().getNombre(),
                        listRes.getMensaje(),
                        listRes.getFechaCreacion(),
                        listRes.getSolucion()
                );
               datosObtenerRespuestaList.add(datosObtenerRespuesta);
            }
            datosObtenerTopicos = new DatosObtenerTopicos(list.getId(), list.getTitulo(), list.getMensaje(),
                    list.getFechaCreacion(), list.getStatus(), list.getAutor().getNombre(), list.getCurso().getNombre(),datosObtenerRespuestaList);
            datosObtenerTopicosList.add(datosObtenerTopicos);

        }
        return datosObtenerTopicosList;
    }



    @Override
    public DatosObtenerTopico obtenerTopico(Long id) {
        Topico topico = topicoRepository.findById(id).get();
        System.out.println(topico.getRespuestas().stream().toList());
        return new DatosObtenerTopico(topico);    }

    @Override
    public DatosModificarTopico modificarTopico(DatosModificarTopico datosModificarTopico) {
        var topico = topicoRepository.getReferenceById(datosModificarTopico.id());
        topico.modificar(datosModificarTopico);
        return new DatosModificarTopico(topico);    }

    @Override
    public void eliminarTopico(Long id) {
//        topicoRepository.deleteById(id);
        var topico = topicoRepository.findById(id);

        if (topico.isPresent()) {
            Topico topico1 = topico.get();
            topicoRepository.delete(topico1);
        } else {
            throw new EntityNotFoundException(String.format("el topico con el id %d no fue encontrado", id));
        }
    }
}
