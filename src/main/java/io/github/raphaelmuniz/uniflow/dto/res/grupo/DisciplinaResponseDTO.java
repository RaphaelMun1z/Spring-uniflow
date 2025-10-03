package io.github.raphaelmuniz.uniflow.dto.res.grupo;

import io.github.raphaelmuniz.uniflow.entities.grupo.Disciplina;
import io.github.raphaelmuniz.uniflow.entities.embeddables.PeriodoLetivo;
import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;

public record DisciplinaResponseDTO(
        String id,
        String nome,
        Integer periodo,
        DificuldadeEnum dificuldade,
        PeriodoLetivo periodoLetivo
) {
    public static DisciplinaResponseDTO fromEntity(Disciplina disciplina) {
        return new DisciplinaResponseDTO(
                disciplina.getId(),
                disciplina.getNome(),
                disciplina.getPeriodo(),
                disciplina.getDificuldade(),
                disciplina.getPeriodoLetivo()
        );
    }
}