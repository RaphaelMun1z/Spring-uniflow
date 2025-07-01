package io.github.raphaelmuniz.plansFy.repositories;

import io.github.raphaelmuniz.plansFy.entities.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacaoRepository extends JpaRepository<Notificacao, String> {
}
