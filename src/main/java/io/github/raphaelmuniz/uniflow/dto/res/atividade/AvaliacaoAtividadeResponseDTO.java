package io.github.raphaelmuniz.uniflow.dto.res.atividade;

import io.github.raphaelmuniz.uniflow.entities.atividade.AvaliacaoAtividade;
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

    public AvaliacaoAtividadeResponseDTO(AvaliacaoAtividade avaliacao) {
        this.id = avaliacao.getId();
        this.titulo = avaliacao.getAtividadeAvaliada().getTitulo();
        this.descricao = avaliacao.getAtividadeAvaliada().getDescricao();
        this.nota = avaliacao.getNota();
        this.feedback = avaliacao.getFeedback();
        this.dataAvaliacao = avaliacao.getDataAvaliacao();
    }
}
