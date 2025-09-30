package io.github.raphaelmuniz.uniflow.dto.res;

import io.github.raphaelmuniz.uniflow.entities.AtividadeEstudante;
import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtividadeEstudanteResponseDTO {
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

    public AtividadeEstudanteResponseDTO(AtividadeEstudante atividadeEstudante) {
        this.id = atividadeEstudante.getId();
        this.dataLancamento = atividadeEstudante.getDataLancamento();
        this.prazoEntrega = atividadeEstudante.getPrazoEntrega();
        this.titulo = atividadeEstudante.getTitulo();
        this.descricao = atividadeEstudante.getDescricao();
        this.dificuldade = atividadeEstudante.getDificuldade();
        this.disciplinaId = atividadeEstudante.getDisciplina().getId();
        this.statusEntrega = atividadeEstudante.getStatusEntrega();
        this.estudanteDonoId = atividadeEstudante.getEstudanteDono().getId();
        this.nota = atividadeEstudante.getNota();
        this.feedback = atividadeEstudante.getFeedback();
    }

    public AtividadeEstudante toModel() {
        return new AtividadeEstudante(dataLancamento, prazoEntrega, titulo, descricao, dificuldade, null, null, statusEntrega, null, null);
    }
}
