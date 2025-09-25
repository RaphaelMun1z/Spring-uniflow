package io.github.raphaelmuniz.uniflow.repositories;

import io.github.raphaelmuniz.uniflow.entities.Estudante;
import io.github.raphaelmuniz.uniflow.entities.Professor;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, String> {
    Optional<Professor> findByEmail(String email);

    boolean existsByEmail(String email);

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"assinaturas", "atividadesAssinante", "inscricoesGrupos"})
    List<Professor> findAll();

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"assinaturas", "atividadesAssinante", "inscricoesGrupos"})
    Optional<Professor> findById(String id);
}
