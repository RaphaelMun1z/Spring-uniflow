package io.github.raphaelmuniz.uniflow.dto.req.atividade;

import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.VisivibilidadeAtividadeEnum;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record AtividadeAvaliativaRequestDTO(
        @NotBlank(message = "O título é obrigatório.")
        String titulo,

        @NotBlank(message = "A descrição é obrigatória.")
        String descricao,

        @NotNull(message = "O prazo de entrega é obrigatório.")
        @Future(message = "O prazo de entrega deve ser no futuro.")
        LocalDateTime prazoEntrega,

        @NotNull(message = "A nota máxima é obrigatória.")
        @Positive(message = "A nota máxima deve ser um valor positivo.")
        Double notaMaxima,

        @NotNull(message = "O campo 'permiteEnvioAtrasado' é obrigatório.")
        Boolean permiteEnvioAtrasado,

        @NotNull(message = "A dificuldade é obrigatória.")
        DificuldadeEnum dificuldade,

        @NotNull(message = "A visibilidade é obrigatória.")
        VisivibilidadeAtividadeEnum visibilidadeAtividade,

        String idAtividadeParaReutilizar
) {
}