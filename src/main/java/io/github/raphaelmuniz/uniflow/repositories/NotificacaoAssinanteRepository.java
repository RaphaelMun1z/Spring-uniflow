package io.github.raphaelmuniz.uniflow.repositories;

import io.github.raphaelmuniz.uniflow.entities.NotificacaoAssinante;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NotificacaoAssinanteRepository extends JpaRepository<NotificacaoAssinante, String> {
    @EntityGraph(attributePaths = {"notificacao"})
    List<NotificacaoAssinante> findByAssinanteId(String assinanteId);

    @Modifying
    @Transactional
    @Query("UPDATE NotificacaoAssinante na SET na.foiLido = true, na.dataLeitura = :dataAtual " +
            "WHERE na.id = :notificacaoId " +
            "AND na.assinante.id = :assinanteId " +
            "AND na.foiLido = false")
    int marcarComoLida(
            @Param("notificacaoId") String notificacaoId,
            @Param("assinanteId") String assinanteId,
            @Param("dataAtual") LocalDateTime dataAtual
    );

    long countAllByAssinante_Id(String assinanteId);
}
