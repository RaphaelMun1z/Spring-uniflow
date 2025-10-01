package io.github.raphaelmuniz.uniflow.repositories.atividade;

import io.github.raphaelmuniz.uniflow.entities.atividade.AvaliacaoAtividade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoAtividadeRepository extends JpaRepository<AvaliacaoAtividade, String> {
}
