package io.github.raphaelmuniz.uniflow.repositories.grupo;

import io.github.raphaelmuniz.uniflow.entities.grupo.InscricaoGrupo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InscricaoGrupoRepository extends JpaRepository<InscricaoGrupo, String> {
    Optional<InscricaoGrupo> findByGrupo_IdAndEstudanteMembro_Id(String grupoId, String assinanteId);

    Page<InscricaoGrupo> findByMembro_Id(String assinanteId, Pageable pageable);
}
