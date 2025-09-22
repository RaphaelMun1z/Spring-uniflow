package io.github.raphaelmuniz.uniflow.repositories;

import io.github.raphaelmuniz.uniflow.entities.AssinaturaUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssinaturaUsuarioRepository extends JpaRepository<AssinaturaUsuario, String> {
}
