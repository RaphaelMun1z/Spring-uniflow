package io.github.raphaelmuniz.uniflow.repositories;

import io.github.raphaelmuniz.uniflow.entities.Assinante;
import io.github.raphaelmuniz.uniflow.entities.Estudante;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssinanteRepository extends JpaRepository<Assinante, String> {
    @Override
    @NonNull
    @EntityGraph(attributePaths = {"assinaturas", "atividadesAssinante", "inscricoesGrupos"})
    List<Assinante> findAll();

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"assinaturas", "atividadesAssinante", "inscricoesGrupos"})
    Optional<Assinante> findById(String id);
}
