package io.github.raphaelmuniz.uniflow.repositories;

import io.github.raphaelmuniz.uniflow.entities.AtividadeEstudante;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AtividadeEstudanteRepository extends JpaRepository<AtividadeEstudante, String> {
    @Query("""
                  SELECT ae FROM AtividadeEstudante ae\s
                  WHERE ae.estudanteDono.id = :estudanteId
                  AND ae.atividadeGrupoOrigem.grupoPublicado.id = :grupoId\s
            \s""")
    List<AtividadeEstudante> findAllByEstudanteAndGrupoOrigem(
            @Param("estudanteId") String estudanteId,
            @Param("grupoId") String grupoId
    );

    @Query("SELECT ae FROM AtividadeEstudante ae " +
            "WHERE ae.estudanteDono.id = :estudanteId " +
            "AND ae.atividadeGrupoOrigem.grupoPublicado.id = :grupoId " +
            "AND ae.statusEntrega = :status")
    List<AtividadeEstudante> findAllByEstudanteAndGrupoOrigemAndStatus(
            @Param("estudanteId") String estudanteId,
            @Param("grupoId") String grupoId,
            @Param("status") StatusEntregaEnum status
    );

    @EntityGraph(attributePaths = {"atividadeGrupoOrigem"})
    List<AtividadeEstudante> findByEstudanteDonoId(String estudanteDonoId);

    @Query("""
              SELECT CASE WHEN COUNT(g) > 0 THEN TRUE ELSE FALSE END\s
              FROM AtividadeEstudante ae
              JOIN ae.atividadeGrupoOrigem ag
              JOIN ag.grupoPublicado g
              JOIN g.criador p\s
              WHERE ae.id = :atividadeId\s
              AND p.id = :professorId\s
              AND TYPE(p) = Professor
            \s""")
    boolean isProfessorCriadorDoGrupoDaAtividade(
            @Param("professorId") String professorId,
            @Param("atividadeId") String atividadeId
    );

    List<AtividadeEstudante> findByAtividadeGrupoOrigemId(String atividadeGrupoOrigemId);

    boolean existsByEstudanteDonoIdAndAtividadeGrupoOrigemId(String estudanteId, String atividadeGrupoId);
}
