package io.github.raphaelmuniz.uniflow.repositories.grupo;

import io.github.raphaelmuniz.uniflow.entities.grupo.Grupo;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GrupoRepository extends JpaRepository<Grupo, String> {
    @Override
    @NonNull
    @EntityGraph(attributePaths = {"atividadesPublicadas", "inscricoes"})
    List<Grupo> findAll();

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"atividadesPublicadas", "inscricoes"})
    Optional<Grupo> findById(@NonNull String id);

    long countByAssinanteCriadorGrupoId(String assinanteId);

    boolean existsByDisciplina_Id(String id);
}