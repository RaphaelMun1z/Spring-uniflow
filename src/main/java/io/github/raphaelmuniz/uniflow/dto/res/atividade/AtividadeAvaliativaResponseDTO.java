package io.github.raphaelmuniz.uniflow.dto.res.atividade;

import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeAvaliativa;
import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.VisivibilidadeAtividadeEnum;

import java.time.LocalDateTime;

public record AtividadeAvaliativaResponseDTO(
        String id,
        LocalDateTime dataLancamento,
        LocalDateTime prazoEntrega,
        String titulo,
        String descricao,
        DificuldadeEnum dificuldade,
        VisivibilidadeAtividadeEnum visibilidadeAtividade,
        String grupoId,
        String criadorId
) {

    public static AtividadeAvaliativaResponseDTO fromEntity(AtividadeAvaliativa atividade) {
        if (atividade == null) {
            return null;
        }
        return new AtividadeAvaliativaResponseDTO(
                atividade.getId(),
                atividade.getDataLancamento(),
                atividade.getPrazoEntrega(),
                atividade.getTitulo(),
                atividade.getDescricao(),
                atividade.getDificuldade(),
                atividade.getVisivibilidadeAtividade(),
                atividade.getGrupoPublicado().getId(),
                atividade.getAssinanteCriadorAtividade().getId()
        );
    }
}