package io.github.raphaelmuniz.plansFy.entities.embeddables;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Embeddable
@Data
public class PeriodoLetivo {
    @NotNull
    private Integer ano;

    @NotNull
    private Integer semestre;
}
