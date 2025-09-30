package io.github.raphaelmuniz.uniflow.repositories.grupo;

import io.github.raphaelmuniz.uniflow.entities.grupo.Grupo;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    Optional<Grupo> findById(String id);

    @EntityGraph(attributePaths = {"inscricoes", "inscricoes.estudanteMembro"})
    @Query("SELECT g FROM Grupo g WHERE g.id = :id")
    Optional<Grupo> findByIdWithMembros(@Param("id") String id);
}