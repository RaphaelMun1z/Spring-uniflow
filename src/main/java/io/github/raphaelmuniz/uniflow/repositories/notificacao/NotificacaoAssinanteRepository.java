package io.github.raphaelmuniz.uniflow.repositories.notificacao;

import io.github.raphaelmuniz.uniflow.entities.notificacao.NotificacaoAssinante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacaoAssinanteRepository extends JpaRepository<NotificacaoAssinante, String> {
    Page<NotificacaoAssinante> findByDestinatario_IdOrderByNotificacao_DataCriacaoDesc(String id, Pageable pageable);

    List<NotificacaoAssinante> findAllByDestinatario_IdAndLidaIsFalse(String id);

    long countByDestinatario_IdAndLidaIsFalse(String destinatarioId);
}
