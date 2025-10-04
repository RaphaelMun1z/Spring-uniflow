package io.github.raphaelmuniz.uniflow.entities.embeddables;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@Embeddable
public record PeriodoLetivo(
        @NotNull(message = "O ano do período letivo é obrigatório.")
        @Min(value = 2000, message = "O ano deve ser igual ou superior a 2000.")
        Integer ano,

        @NotNull(message = "O semestre do período letivo é obrigatório.")
        @Min(value = 1, message = "O semestre deve ser 1.")
        @Max(value = 2, message = "O semestre deve ser 2.")
        Integer semestre
) implements Serializable {
    public PeriodoLetivo() {
        this(null, null);
    }
}