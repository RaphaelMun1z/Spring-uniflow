package io.github.raphaelmuniz.uniflow.dto.res.atividade;

import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeEntrega;
import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtividadeEntregaResponseDTO {
    private String id;
    private LocalDateTime dataLancamento;
    private LocalDateTime prazoEntrega;
    private String titulo;
    private String descricao;
    private DificuldadeEnum dificuldade;
    private String disciplinaId;
    private StatusEntregaEnum statusEntrega;
    private String estudanteDonoId;
    private Double nota;
    private String feedback;

    public AtividadeEntregaResponseDTO(AtividadeEntrega atividadeEntrega) {
        this.id = atividadeEntrega.getId();
        this.dataLancamento = atividadeEntrega.getDataLancamento();
        this.prazoEntrega = atividadeEntrega.getPrazoEntrega();
        this.titulo = atividadeEntrega.getTitulo();
        this.descricao = atividadeEntrega.getDescricao();
        this.dificuldade = atividadeEntrega.getDificuldade();
        this.disciplinaId = atividadeEntrega.getDisciplina().getId();
        this.statusEntrega = atividadeEntrega.getStatusEntrega();
        this.estudanteDonoId = atividadeEntrega.getEstudanteDono().getId();
    }

    public AtividadeEntrega toModel() {
        return new AtividadeEntrega(dataLancamento, prazoEntrega, titulo, descricao, dificuldade, null, null, statusEntrega, null, null);
    }
}
