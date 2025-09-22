package io.github.raphaelmuniz.uniflow.repositories;

import io.github.raphaelmuniz.uniflow.entities.InscricaoGrupo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InscricaoGrupoRepository extends JpaRepository<InscricaoGrupo, String> {
    Optional<InscricaoGrupo> findByGrupo_IdAndInscrito_Id(String grupoId, String assinanteId);
    List<InscricaoGrupo> findAllByInscrito_Id(String assinanteId);
}
