package io.github.raphaelmuniz.uniflow.dto.req.atividade;

import io.github.raphaelmuniz.uniflow.dto.req.RequestData;
import io.github.raphaelmuniz.uniflow.entities.atividade.Disciplina;
import io.github.raphaelmuniz.uniflow.entities.embeddables.PeriodoLetivo;
import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DisciplinaRequestDTO implements RequestData<Disciplina> {
    @NotBlank
    private String nome;

    @NotNull
    private Integer periodo;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DificuldadeEnum dificuldade;

    @NotNull
    @Valid
    @Embedded
    private PeriodoLetivo periodoLetivo;

    public Disciplina toModel(){
        return new Disciplina(null, nome, periodo, dificuldade, periodoLetivo);
    }
}
