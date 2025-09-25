package io.github.raphaelmuniz.uniflow.repositories;

import io.github.raphaelmuniz.uniflow.entities.Assinante;
import io.github.raphaelmuniz.uniflow.entities.Grupo;
import io.github.raphaelmuniz.uniflow.entities.Professor;
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

    @Query("SELECT g FROM Grupo g LEFT JOIN FETCH g.inscricoes i LEFT JOIN FETCH i.membro WHERE g.id = :id")
    Optional<Grupo> findByIdComMembros(@Param("id") String id);
}