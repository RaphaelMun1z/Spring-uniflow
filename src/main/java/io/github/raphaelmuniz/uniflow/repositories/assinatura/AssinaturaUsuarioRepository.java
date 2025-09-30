package io.github.raphaelmuniz.uniflow.repositories.assinatura;

import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AssinaturaUsuarioRepository extends JpaRepository<AssinaturaUsuario, String> {
    Optional<AssinaturaUsuario> findFirstByAssinanteIdAndStatusIsTrueAndDataExpiracaoAfter(
            String assinanteId,
            LocalDateTime agora
    );

    Optional<AssinaturaUsuario> findByAssinanteId(String assinanteId);
}
