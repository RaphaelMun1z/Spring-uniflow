package io.github.raphaelmuniz.uniflow.repositories;

import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeEntrega;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AtividadeEntregaRepository extends JpaRepository<AtividadeEntrega, String> {
    @Query("""
                  SELECT ae FROM AtividadeEntrega ae\s
                  WHERE ae.estudanteDono.id = :estudanteId
                  AND ae.atividadeAvaliativaOrigem.grupoPublicado.id = :grupoId\s
            \s""")
    List<AtividadeEntrega> findAllByEstudanteAndGrupoOrigem(
            @Param("estudanteId") String estudanteId,
            @Param("grupoId") String grupoId
    );

    @Query("SELECT ae FROM AtividadeEntrega ae " +
            "WHERE ae.estudanteDono.id = :estudanteId " +
            "AND ae.atividadeAvaliativaOrigem.grupoPublicado.id = :grupoId " +
            "AND ae.statusEntrega = :status")
    List<AtividadeEntrega> findAllByEstudanteAndGrupoOrigemAndStatus(
            @Param("estudanteId") String estudanteId,
            @Param("grupoId") String grupoId,
            @Param("status") StatusEntregaEnum status
    );

    @EntityGraph(attributePaths = {"atividadeAvaliativaOrigem"})
    List<AtividadeEntrega> findByEstudanteDonoId(String estudanteDonoId);

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

    List<AtividadeEntrega> findByAtividadeAvaliativaOrigemId(String atividadeAvaliativaOrigemId);

    boolean existsByEstudanteDonoIdAndAtividadeAvaliativaOrigemId(String estudanteId, String atividadeAvaliativaId);
}
