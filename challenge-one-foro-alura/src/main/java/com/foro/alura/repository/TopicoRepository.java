package com.foro.alura.repository;

import com.foro.alura.modelo.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<Topico,Long> {
    boolean existsByTituloOrMensaje(String titulo, String mensaje);
}