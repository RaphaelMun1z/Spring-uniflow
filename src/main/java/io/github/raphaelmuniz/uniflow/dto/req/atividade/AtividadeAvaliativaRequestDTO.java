package io.github.raphaelmuniz.uniflow.dto.req.atividade;

import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.VisivibilidadeAtividadeEnum;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record AtividadeAvaliativaRequestDTO(
        String titulo,
        String descricao,
        @NotNull @Future LocalDateTime prazoEntrega,
        @NotNull @Positive Double notaMaxima,
        @NotNull Boolean permiteEnvioAtrasado,
        @NotNull DificuldadeEnum dificuldade,
        @NotNull VisivibilidadeAtividadeEnum visibilidadeAtividade,

        String idAtividadeParaReutilizar
) {
}