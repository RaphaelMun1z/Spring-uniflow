package io.github.raphaelmuniz.uniflow.repositories.usuario;

import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssinanteRepository extends JpaRepository<Assinante, String> {
    @Override
    @NonNull
    @EntityGraph(attributePaths = {"assinaturas"})
    List<Assinante> findAll();

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"assinaturas"})
    Optional<Assinante> findById(String id);
}
