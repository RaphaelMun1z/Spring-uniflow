package io.github.raphaelmuniz.uniflow.repositories.assinatura;

import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaUsuario;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusAssinaturaUsuarioEnum;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AssinaturaUsuarioRepository extends JpaRepository<AssinaturaUsuario, String> {
    @Query("SELECT au FROM AssinaturaUsuario au " +
            "LEFT JOIN FETCH au.assinaturaModelo " +
            "WHERE au.assinante.id = :assinanteId " +
            "AND au.status IN :statusVigentes " +
            "AND au.dataExpiracao > :agora " +
            "ORDER BY au.dataInicio DESC")
    Optional<AssinaturaUsuario> findFirstVigenteByAssinanteId(
            @Param("assinanteId") String assinanteId,
            @Param("statusVigentes") List<StatusAssinaturaUsuarioEnum> statusVigentes,
            @Param("agora") LocalDateTime agora
    );

    @EntityGraph(attributePaths = {"assinaturaModelo"})
    List<AssinaturaUsuario> findByAssinanteId(String assinanteId);
}
