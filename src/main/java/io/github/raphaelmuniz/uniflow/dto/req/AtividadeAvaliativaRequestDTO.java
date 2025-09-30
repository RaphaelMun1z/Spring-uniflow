package io.github.raphaelmuniz.uniflow.dto.req;

import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeAvaliativa;
import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.VisivibilidadeAtividadeAvaliativaEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AtividadeAvaliativaRequestDTO {
    private LocalDateTime dataLancamento;
    private LocalDateTime prazoEntrega;
    private String titulo;
    private String descricao;
    private DificuldadeEnum dificuldade;
    private VisivibilidadeAtividadeAvaliativaEnum visivibilidade;
    private String grupoId;
    private String criadorId;
    private String disciplinaId;

    public AtividadeAvaliativa toModel() {
        return new AtividadeAvaliativa(dataLancamento, prazoEntrega, titulo, descricao, dificuldade, null, null, visivibilidade, null, null);
    }
}
