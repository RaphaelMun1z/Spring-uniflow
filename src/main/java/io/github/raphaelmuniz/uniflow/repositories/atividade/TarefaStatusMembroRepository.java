package io.github.raphaelmuniz.uniflow.repositories.atividade;

import io.github.raphaelmuniz.uniflow.entities.atividade.TarefaStatusMembro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TarefaStatusMembroRepository extends JpaRepository<TarefaStatusMembro, String> {
    int countByAtividadeColaborativa_IdAndStatus(String id, String string);

    Optional<TarefaStatusMembro> findByAtividadeColaborativa_IdAndMembro_Id(String atividadeId, String id);
}
