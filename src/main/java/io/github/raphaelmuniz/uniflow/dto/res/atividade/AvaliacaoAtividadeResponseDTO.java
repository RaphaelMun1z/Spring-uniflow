package io.github.raphaelmuniz.uniflow.dto.res.atividade;

import io.github.raphaelmuniz.uniflow.entities.atividade.AvaliacaoAtividade;

import java.time.LocalDateTime;

public record AvaliacaoAtividadeResponseDTO(
        String id,
        Double nota,
        String feedback,
        LocalDateTime dataAvaliacao,
        String professorAvaliadorNome
) {
    public static AvaliacaoAtividadeResponseDTO fromEntity(AvaliacaoAtividade avaliacao) {
        if (avaliacao == null) {
            return null;
        }
        return new AvaliacaoAtividadeResponseDTO(
                avaliacao.getId(),
                avaliacao.getNota(),
                avaliacao.getFeedback(),
                avaliacao.getDataAvaliacao(),
                avaliacao.getProfessorAvaliador().getNome()
        );
    }
}