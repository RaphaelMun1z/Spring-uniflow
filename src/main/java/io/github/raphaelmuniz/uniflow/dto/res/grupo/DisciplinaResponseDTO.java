package io.github.raphaelmuniz.uniflow.dto.res.grupo;

import io.github.raphaelmuniz.uniflow.entities.grupo.Disciplina;
import io.github.raphaelmuniz.uniflow.entities.embeddables.PeriodoLetivo;
import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DisciplinaResponseDTO {
    private String id;
    private String nome;
    private Integer periodo;

    @Enumerated(EnumType.STRING)
    private DificuldadeEnum dificuldade;

    @Embedded
    private PeriodoLetivo periodoLetivo;

    public DisciplinaResponseDTO(Disciplina disciplina) {
        this.id = disciplina.getId();
        this.nome = disciplina.getNome();
        this.periodo = disciplina.getPeriodo();
        this.dificuldade = disciplina.getDificuldade();
        this.periodoLetivo = disciplina.getPeriodoLetivo();
    }

    public Disciplina toModel() {
        return new Disciplina(id, nome, periodo, dificuldade, periodoLetivo, null);
    }
}
