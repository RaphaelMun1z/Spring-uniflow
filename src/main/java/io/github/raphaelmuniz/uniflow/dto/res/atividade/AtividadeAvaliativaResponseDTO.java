package io.github.raphaelmuniz.uniflow.dto.res.atividade;

import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeAvaliativa;
import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.VisivibilidadeAtividadeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtividadeAvaliativaResponseDTO {
    private String id;
    private LocalDateTime dataLancamento;
    private LocalDateTime prazoEntrega;
    private String titulo;
    private String descricao;
    private DificuldadeEnum dificuldade;
    private VisivibilidadeAtividadeEnum visivibilidade;
    private String grupoId;
    private String criadorId;

    public AtividadeAvaliativaResponseDTO(AtividadeAvaliativa atividadeAvaliativa) {
        this.id = atividadeAvaliativa.getId();
        this.dataLancamento = atividadeAvaliativa.getDataLancamento();
        this.prazoEntrega = atividadeAvaliativa.getPrazoEntrega();
        this.titulo = atividadeAvaliativa.getTitulo();
        this.descricao = atividadeAvaliativa.getDescricao();
        this.dificuldade = atividadeAvaliativa.getDificuldade();
        this.visivibilidade = atividadeAvaliativa.getVisivibilidadeAtividade();
        this.grupoId = atividadeAvaliativa.getGrupoPublicado().getId();
        this.criadorId = atividadeAvaliativa.getAssinanteCriadorAtividade().getId();
    }

    public AtividadeAvaliativa toModel() {
        return new AtividadeAvaliativa(dataLancamento, prazoEntrega, titulo, descricao, dificuldade, null, id, visivibilidade, null, null);
    }
}
