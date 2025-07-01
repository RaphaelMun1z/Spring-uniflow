package io.github.raphaelmuniz.plansFy.dto.req;

import io.github.raphaelmuniz.plansFy.entities.Assinante;
import io.github.raphaelmuniz.plansFy.entities.AtividadeCopia;
import io.github.raphaelmuniz.plansFy.entities.Disciplina;
import io.github.raphaelmuniz.plansFy.entities.Grupo;
import io.github.raphaelmuniz.plansFy.entities.enums.DificuldadeEnum;
import io.github.raphaelmuniz.plansFy.entities.enums.StatusEntregaEnum;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AtividadeCopiaRequestDTO {
    private LocalDateTime dataLancamento;
    private LocalDateTime prazoEntrega;
    private String titulo;
    private String descricao;
    private DificuldadeEnum dificuldade;
    private Disciplina disciplina;
    private StatusEntregaEnum status;
    private Grupo grupo;
    private Assinante assinante;

    public AtividadeCopia toModel() {
        return new AtividadeCopia(dataLancamento, prazoEntrega, titulo, descricao, dificuldade, disciplina, null, status, grupo, assinante);
    }
}
