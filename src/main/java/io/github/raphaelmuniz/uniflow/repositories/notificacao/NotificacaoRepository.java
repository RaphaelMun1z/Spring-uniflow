package io.github.raphaelmuniz.uniflow.repositories.notificacao;

import io.github.raphaelmuniz.uniflow.entities.notificacao.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacaoRepository extends JpaRepository<Notificacao, String> {
}
