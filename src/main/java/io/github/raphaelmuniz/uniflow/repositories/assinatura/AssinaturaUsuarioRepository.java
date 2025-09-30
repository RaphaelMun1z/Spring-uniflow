package io.github.raphaelmuniz.uniflow.repositories.assinatura;

import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaModelo;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AssinaturaUsuarioRepository extends JpaRepository<AssinaturaUsuario, String> {
    Optional<AssinaturaUsuario> findFirstByAssinanteIdAndStatusIsTrueAndDataExpiracaoAfter(
            String assinanteId,
            LocalDateTime agora
    );

    Optional<AssinaturaUsuario> findByAssinanteId(String assinanteId);

    @Query("SELECT au.assinaturaModelo FROM AssinaturaUsuario au WHERE au.assinante.id = :assinanteId AND au.status = 'ATIVA'")
    Optional<AssinaturaModelo> findAssinaturaModeloAtivaByAssinanteId(@Param("assinanteId") String assinanteId);
}
