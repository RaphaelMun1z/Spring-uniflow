package io.github.raphaelmuniz.uniflow.repositories.usuario;

import io.github.raphaelmuniz.uniflow.entities.usuario.Estudante;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EstudanteRepository extends JpaRepository<Estudante, String> {
    Optional<Estudante> findByEmail(String email);

    boolean existsByEmail(String email);

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"assinaturas", "atividadesAssinante", "inscricoesGrupos"})
    List<Estudante> findAll();

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"assinaturas", "atividadesAssinante", "inscricoesGrupos"})
    Optional<Estudante> findById(String id);
}
