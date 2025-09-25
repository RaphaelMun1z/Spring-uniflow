package io.github.raphaelmuniz.uniflow.repositories;

import io.github.raphaelmuniz.uniflow.entities.AtividadeAssinante;
import io.github.raphaelmuniz.uniflow.entities.AtividadeGrupo;
import io.github.raphaelmuniz.uniflow.entities.Estudante;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AtividadeAssinanteRepository extends JpaRepository<AtividadeAssinante, String> {

}
