package io.github.raphaelmuniz.uniflow.repositories;

import io.github.raphaelmuniz.uniflow.entities.AtividadeAssinante;
import io.github.raphaelmuniz.uniflow.entities.AtividadeGrupo;
import io.github.raphaelmuniz.uniflow.entities.Estudante;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AtividadeAssinanteRepository extends JpaRepository<AtividadeAssinante, String> {
    @Query("SELECT aa FROM AtividadeAssinante aa " +
            "WHERE aa.assinanteDono.id = :assinanteId " +
            "AND aa.atividadeGrupoOrigem.grupoPublicado.id = :grupoId")
    List<AtividadeAssinante> findAllByAssinanteAndGrupoOrigem(
            @Param("assinanteId") String assinanteId,
            @Param("grupoId") String grupoId
    );

    @Query("SELECT aa FROM AtividadeAssinante aa " +
            "WHERE aa.assinanteDono.id = :assinanteId " +
            "AND aa.atividadeGrupoOrigem.grupoPublicado.id = :grupoId " +
            "AND aa.statusEntrega = :status")
    List<AtividadeAssinante> findAllByAssinanteAndGrupoOrigemAndStatus(
            @Param("assinanteId") String assinanteId,
            @Param("grupoId") String grupoId,
            @Param("status") StatusEntregaEnum status
    );

    @EntityGraph(attributePaths = {"atividadeGrupoOrigem"})
    List<AtividadeAssinante> findByAssinanteDonoId(String assinanteId);

    boolean existsByAssinanteDonoIdAndAtividadeGrupoOrigemId(String assinanteId, String atividadeGrupoId);
}
