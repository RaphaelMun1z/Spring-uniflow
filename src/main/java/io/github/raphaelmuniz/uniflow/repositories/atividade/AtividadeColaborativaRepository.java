package io.github.raphaelmuniz.uniflow.repositories.atividade;

import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeColaborativa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AtividadeColaborativaRepository extends JpaRepository<AtividadeColaborativa, String> {
    List<AtividadeColaborativa> findByGrupo_Id(String id);

    @Query("SELECT ac FROM AtividadeColaborativa ac LEFT JOIN FETCH ac.statusMembros WHERE ac.id = :id")
    Optional<AtividadeColaborativa> findByIdWithStatusMembros(@Param("id") String id);
}
