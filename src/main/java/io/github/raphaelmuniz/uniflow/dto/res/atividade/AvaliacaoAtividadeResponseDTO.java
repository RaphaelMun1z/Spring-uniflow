package io.github.raphaelmuniz.uniflow.dto.res.atividade;

import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeEntrega;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvaliacaoAtividadeResponseDTO {
    private String id;
    private String titulo;
    private String descricao;
    private Double nota;
    private String feedback;
    private LocalDateTime dataAvaliacao;

    public AvaliacaoAtividadeResponseDTO(AtividadeEntrega atividadeEntrega) {
        this.id = atividadeEntrega.getId();
        this.titulo = atividadeEntrega.getTitulo();
        this.descricao = atividadeEntrega.getDescricao();
        this.nota = atividadeEntrega.getAvaliacaoAtividade().getNota();
        this.feedback = atividadeEntrega.getAvaliacaoAtividade().getFeedback();
        this.dataAvaliacao = atividadeEntrega.getAvaliacaoAtividade().getDataAvaliacao();
    }
}
