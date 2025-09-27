package io.github.raphaelmuniz.uniflow.repositories;

import io.github.raphaelmuniz.uniflow.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Optional<Usuario> findByEmail(String email);
}
