package io.github.raphaelmuniz.uniflow.repositories.usuario;

import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Optional<Usuario> findByEmail(String email);
}
