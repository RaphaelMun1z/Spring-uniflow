package io.github.raphaelmuniz.uniflow.dto.req.atividade;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Length;

public record AvaliacaoRequestDTO(
        @NotNull(message = "A nota é obrigatória.")
        @PositiveOrZero(message = "A nota não pode ser negativa.")
        @Max(value = 100, message = "A nota máxima não pode exceder 100.")
        Double nota,

        @Length(max = 1000, message = "O feedback não pode exceder 1000 caracteres.")
        String feedback
) {}