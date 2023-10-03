package com.foro.alura.service;

import com.foro.alura.dto.topico.DatosModificarTopico;
import com.foro.alura.dto.topico.DatosObtenerTopico;
import com.foro.alura.dto.topico.DatosObtenerTopicos;
import com.foro.alura.dto.topico.DatosRegistroTopico;
import com.foro.alura.modelo.Topico;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;

public interface TopicoService {
    Topico registrarTopico(DatosRegistroTopico datosRegistroTopico);

    List<DatosObtenerTopicos> obtenerTopicos();

    DatosObtenerTopico obtenerTopico (Long id);
    DatosModificarTopico modificarTopico(DatosModificarTopico datosModificarTopico);
    void eliminarTopico (Long id);
}
