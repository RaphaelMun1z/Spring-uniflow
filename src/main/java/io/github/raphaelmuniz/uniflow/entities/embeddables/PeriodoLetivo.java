package io.github.raphaelmuniz.uniflow.entities.embeddables;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeriodoLetivo implements Serializable {
    @NotNull
    private Integer ano;

    @NotNull
    private Integer semestre;
}
