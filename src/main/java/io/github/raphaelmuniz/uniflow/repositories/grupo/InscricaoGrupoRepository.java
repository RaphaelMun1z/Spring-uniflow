package io.github.raphaelmuniz.uniflow.repositories.grupo;

import io.github.raphaelmuniz.uniflow.entities.grupo.InscricaoGrupo;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InscricaoGrupoRepository extends JpaRepository<InscricaoGrupo, String> {
    Optional<InscricaoGrupo> findByGrupo_IdAndEstudanteMembro_Id(String grupoId, String assinanteId);

    @EntityGraph(attributePaths = {"grupo", "grupo.criador"})
    List<InscricaoGrupo> findAllByEstudanteMembro_Id(String membroId);
}
