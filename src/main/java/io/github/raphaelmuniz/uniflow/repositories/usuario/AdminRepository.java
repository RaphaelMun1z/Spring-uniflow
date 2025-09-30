package io.github.raphaelmuniz.uniflow.repositories.usuario;

import io.github.raphaelmuniz.uniflow.entities.usuario.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, String> {
}
