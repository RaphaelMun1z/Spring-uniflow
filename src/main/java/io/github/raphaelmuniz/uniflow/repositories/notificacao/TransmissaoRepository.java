package io.github.raphaelmuniz.uniflow.repositories.notificacao;

import io.github.raphaelmuniz.uniflow.entities.notificacao.Transmissao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransmissaoRepository extends JpaRepository<Transmissao, String> {
}
