package io.github.raphaelmuniz.uniflow.repositories.usuario;

import io.github.raphaelmuniz.uniflow.entities.usuario.Estudante;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EstudanteRepository extends JpaRepository<Estudante, String> {
    boolean existsByEmail(String email);

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"assinaturas", "atividadesEstudante", "inscricoesGrupos"})
    List<Estudante> findAll();

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"assinaturas", "atividadesEstudante", "inscricoesGrupos"})
    Optional<Estudante> findById(@NonNull String id);
}
