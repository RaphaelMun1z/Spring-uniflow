package io.github.raphaelmuniz.uniflow.dto.res.atividade;

import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeAvaliativa;
import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeEntrega;
import io.github.raphaelmuniz.uniflow.entities.atividade.AvaliacaoAtividade;
import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.VisivibilidadeAtividadeEnum;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class AtividadeAvaliativaDetalhadaResponseDTO {
    private final String id;
    private final String titulo;
    private final String descricao;
    private final LocalDateTime dataLancamento;
    private final LocalDateTime prazoEntrega;
    private final Double notaMaxima;
    private final Boolean permiteEnvioAtrasado;
    private final DificuldadeEnum dificuldade;
    private final VisivibilidadeAtividadeEnum visibilidadeAtividade;

    private final List<EntregaResumoDTO> entregasDosAlunos;

    @Getter
    public static class EntregaResumoDTO {
        private final String id;
        private final String alunoId;
        private final String alunoNome;
        private final StatusEntregaEnum status;
        private final LocalDateTime dataEnvio;
        private final Double nota;

        public EntregaResumoDTO(AtividadeEntrega entrega) {
            this.id = entrega.getId();
            this.alunoId = entrega.getEstudanteDono().getId();
            this.alunoNome = entrega.getEstudanteDono().getNome();
            this.status = entrega.getStatusEntrega();
            this.dataEnvio = entrega.getDataEnvio();
            this.nota = Optional.ofNullable(entrega.getAvaliacaoAtividade())
                    .map(AvaliacaoAtividade::getNota)
                    .orElse(null);
        }
    }

    public AtividadeAvaliativaDetalhadaResponseDTO(AtividadeAvaliativa atividade) {
        this.id = atividade.getId();
        this.titulo = atividade.getTitulo();
        this.descricao = atividade.getDescricao();
        this.dataLancamento = atividade.getDataLancamento();
        this.prazoEntrega = atividade.getPrazoEntrega();
        this.notaMaxima = atividade.getNotaMaxima();
        this.permiteEnvioAtrasado = atividade.getPermiteEnvioAtrasado();
        this.dificuldade = atividade.getDificuldade();
        this.visibilidadeAtividade = atividade.getVisivibilidadeAtividade();

        if (atividade.getCopiasDosAssinantes() != null) {
            this.entregasDosAlunos = atividade.getCopiasDosAssinantes().stream()
                    .map(EntregaResumoDTO::new)
                    .collect(Collectors.toList());
        } else {
            this.entregasDosAlunos = new ArrayList<>();
        }
    }
}
