package io.github.raphaelmuniz.uniflow.dto.res;

import io.github.raphaelmuniz.uniflow.entities.AtividadeAssinante;
import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtividadeAssinanteResponseDTO {
    private String id;
    private LocalDateTime dataLancamento;
    private LocalDateTime prazoEntrega;
    private String titulo;
    private String descricao;
    private DificuldadeEnum dificuldade;
    private String disciplinaId;
    private StatusEntregaEnum statusEntrega;
    private String assinanteDonoId;

    public AtividadeAssinanteResponseDTO(AtividadeAssinante atividadeAssinante) {
        this.id = atividadeAssinante.getId();
        this.dataLancamento = atividadeAssinante.getDataLancamento();
        this.prazoEntrega = atividadeAssinante.getPrazoEntrega();
        this.titulo = atividadeAssinante.getTitulo();
        this.descricao = atividadeAssinante.getDescricao();
        this.dificuldade = atividadeAssinante.getDificuldade();
        this.disciplinaId = atividadeAssinante.getDisciplina().getId();
        this.statusEntrega = atividadeAssinante.getStatusEntrega();
        this.assinanteDonoId = atividadeAssinante.getAssinanteDono().getId();
    }

    public AtividadeAssinante toModel() {
        return new AtividadeAssinante(dataLancamento, prazoEntrega, titulo, descricao, dificuldade, null, null, statusEntrega, null);
    }
}
