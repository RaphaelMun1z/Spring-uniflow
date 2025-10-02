package io.github.raphaelmuniz.uniflow.repositories.atividade;

import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeAvaliativa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AtividadeAvaliativaRepository extends JpaRepository<AtividadeAvaliativa, String> {
    @Query("SELECT a FROM AtividadeAvaliativa a LEFT JOIN FETCH a.copiasDosAssinantes WHERE a.id = :id")
    Optional<AtividadeAvaliativa> findByIdWithEntregas(@Param("id") String id);
}
