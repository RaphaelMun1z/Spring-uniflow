package io.github.raphaelmuniz.uniflow.repositories.atividade;

import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeEntrega;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface AtividadeEntregaRepository extends JpaRepository<AtividadeEntrega, String> {
    @Query("""
              SELECT CASE WHEN COUNT(g) > 0 THEN TRUE ELSE FALSE END
              FROM AtividadeEntrega ae
              JOIN ae.atividadeAvaliativaOrigem ag
              JOIN ag.grupoPublicado g
              JOIN g.assinanteCriadorGrupo p
              WHERE ae.id = :atividadeId
              AND p.id = :professorId
              AND TYPE(p) = Professor
            """)
    boolean isProfessorCriadorDoGrupoDaAtividade(
            @Param("professorId") String professorId,
            @Param("atividadeId") String atividadeId
    );

    int countByAtividadeAvaliativaOrigem_IdAndStatusEntregaIn(String id, Set<StatusEntregaEnum> entregue);

    Optional<AtividadeEntrega> findByAtividadeAvaliativaOrigem_IdAndEstudanteDono_Id(String id, String id1);
}
