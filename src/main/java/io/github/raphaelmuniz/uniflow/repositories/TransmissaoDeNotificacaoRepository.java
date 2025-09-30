package io.github.raphaelmuniz.uniflow.repositories;

import io.github.raphaelmuniz.uniflow.entities.notificacao.Transmissao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransmissaoDeNotificacaoRepository extends JpaRepository<Transmissao, String> {
}
