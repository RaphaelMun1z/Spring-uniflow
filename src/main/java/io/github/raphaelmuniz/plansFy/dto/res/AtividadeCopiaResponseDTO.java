package io.github.raphaelmuniz.plansFy.dto.res;

import io.github.raphaelmuniz.plansFy.entities.Assinante;
import io.github.raphaelmuniz.plansFy.entities.AtividadeCopia;
import io.github.raphaelmuniz.plansFy.entities.Disciplina;
import io.github.raphaelmuniz.plansFy.entities.Grupo;
import io.github.raphaelmuniz.plansFy.entities.enums.DificuldadeEnum;
import io.github.raphaelmuniz.plansFy.entities.enums.StatusEntregaEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
