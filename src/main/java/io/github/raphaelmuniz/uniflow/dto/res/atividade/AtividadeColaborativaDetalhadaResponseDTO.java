package io.github.raphaelmuniz.uniflow.dto.res.atividade;

import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeColaborativa;
import io.github.raphaelmuniz.uniflow.entities.atividade.TarefaStatusMembro;
import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AtividadeColaborativaDetalhadaResponseDTO {
    private final String id;
    private final String titulo;
    private final String descricao;
    private final LocalDateTime prazoEntrega;
    private final DificuldadeEnum dificuldade;
    private final List<StatusMembroDTO> statusDosMembros;

    @Getter
    private static class StatusMembroDTO {
        private final String membroId;
        private final String membroNome;
        private final StatusEntregaEnum status;

        public StatusMembroDTO(TarefaStatusMembro tarefa) {
            this.membroId = tarefa.getMembro().getId();
            this.membroNome = tarefa.getMembro().getNome();
            this.status = tarefa.getStatus();
        }
    }

    public AtividadeColaborativaDetalhadaResponseDTO(AtividadeColaborativa atividade) {
        this.id = atividade.getId();
        this.titulo = atividade.getTitulo();
        this.descricao = atividade.getDescricao();
        this.prazoEntrega = atividade.getPrazoEntrega();
        this.dificuldade = atividade.getDificuldade();
        this.statusDosMembros = atividade.getStatusMembros().stream()
            .map(StatusMembroDTO::new)
            .collect(Collectors.toList());
    }
}