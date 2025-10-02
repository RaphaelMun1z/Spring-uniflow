package io.github.raphaelmuniz.uniflow.repositories.atividade;

import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeAvaliativa;
import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeColaborativa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AtividadeColaborativaRepository extends JpaRepository<AtividadeColaborativa, String> {
    List<AtividadeColaborativa> findByGrupo_Id(String id);
}
