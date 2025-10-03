package io.github.raphaelmuniz.uniflow.dto.req.grupo;

import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import jakarta.validation.constraints.*;

public record DisciplinaRequestDTO(
        @NotBlank(message = "O nome da disciplina é obrigatório.")
        String nome,

        @NotNull(message = "O período (semestre) da disciplina é obrigatório.")
        @Positive(message = "O período deve ser um número positivo.")
        Integer periodo,

        @NotNull(message = "O nível de dificuldade é obrigatório.")
        DificuldadeEnum dificuldade,

        @NotNull(message = "O ano do período letivo é obrigatório.")
        Integer anoLetivo,

        @NotNull(message = "O semestre do período letivo é obrigatório.")
        @Min(value = 1, message = "O semestre deve ser 1 ou 2.")
        @Max(value = 2, message = "O semestre deve ser 1 ou 2.")
        Integer semestreLetivo
) {}