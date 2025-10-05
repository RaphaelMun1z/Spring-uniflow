package io.github.raphaelmuniz.uniflow.dto.res.atividade;

import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeEntrega;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class AtividadeEntregaDetalhadaResponseDTO {

    private final String id;
    private final StatusEntregaEnum status;
    private final String textoResposta;
    private final List<String> anexos;
    private final LocalDateTime dataEnvio;
    private final AlunoResumoDTO aluno;
    private final AvaliacaoAtividadeResponseDTO avaliacao;

    public record AlunoResumoDTO(String id, String nome) {
    }

    public AtividadeEntregaDetalhadaResponseDTO(AtividadeEntrega entrega) {
        this.id = entrega.getId();
        this.status = entrega.getStatusEntrega();
        this.textoResposta = entrega.getTextoResposta();
        this.anexos = entrega.getAnexos();
        this.dataEnvio = entrega.getDataEnvio();
        this.aluno = new AlunoResumoDTO(entrega.getEstudanteDono().getId(), entrega.getEstudanteDono().getNome());
        this.avaliacao = AvaliacaoAtividadeResponseDTO.fromEntity(entrega.getAvaliacaoAtividade());
    }
}