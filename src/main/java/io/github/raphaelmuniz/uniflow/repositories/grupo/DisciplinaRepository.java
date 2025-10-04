package io.github.raphaelmuniz.uniflow.repositories.grupo;

import io.github.raphaelmuniz.uniflow.entities.embeddables.PeriodoLetivo;
import io.github.raphaelmuniz.uniflow.entities.grupo.Disciplina;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplinaRepository extends JpaRepository<Disciplina, String> {
    boolean existsByNomeAndPeriodoAndPeriodoLetivo(@NotBlank(message = "O nome da disciplina é obrigatório.") String nome, @NotNull(message = "O período (semestre) da disciplina é obrigatório.") @Positive(message = "O período deve ser um número positivo.") Integer periodo, PeriodoLetivo periodoLetivo);
}
