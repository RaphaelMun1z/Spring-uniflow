package io.github.raphaelmuniz.uniflow.dto.res;

import io.github.raphaelmuniz.uniflow.entities.AtividadeCopia;
import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtividadeCopiaResponseDTO {
    private String id;
    private LocalDateTime dataLancamento;
    private LocalDateTime prazoEntrega;
    private String titulo;
    private String descricao;
    private DificuldadeEnum dificuldade;
    private String disciplinaId;
    private StatusEntregaEnum status;
    private String grupoId;
    private String assinanteId;

    public AtividadeCopiaResponseDTO(AtividadeCopia atividadeCopia) {
        this.id = atividadeCopia.getId();
        this.dataLancamento = atividadeCopia.getDataLancamento();
        this.prazoEntrega = atividadeCopia.getPrazoEntrega();
        this.titulo = atividadeCopia.getTitulo();
        this.descricao = atividadeCopia.getDescricao();
        this.dificuldade = atividadeCopia.getDificuldade();
        this.disciplinaId = atividadeCopia.getDisciplina().getId();
        this.status = atividadeCopia.getStatus();
        if (atividadeCopia.getGrupo() != null)
            this.grupoId = atividadeCopia.getGrupo().getId();

        if (atividadeCopia.getAssinante() != null)
            this.assinanteId = atividadeCopia.getAssinante().getId();
    }

    public AtividadeCopia toModel() {
        return new AtividadeCopia(dataLancamento, prazoEntrega, titulo, descricao, dificuldade, null, id, status, null, null);
    }
}
