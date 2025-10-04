package io.github.raphaelmuniz.uniflow.repositories.usuario;

import io.github.raphaelmuniz.uniflow.entities.usuario.Admin;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, String> {
    Optional<Admin> findByEmail(String email);
}
