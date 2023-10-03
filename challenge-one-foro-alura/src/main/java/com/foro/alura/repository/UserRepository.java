package com.foro.alura.repository;

import com.foro.alura.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByNombre(String nombreUsuario);

}
