package io.github.raphaelmuniz.uniflow.dto.req.grupo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

public record AdicionarAtividadeRequestDTO(
        @NotNull(message = "O tipo da atividade é obrigatório.")
        TipoAtividade tipo,

        @Valid
        DadosAtividadeAvaliativa avaliativa,

        @Valid
        DadosAtividadeColaborativa colaborativa
) {
    public enum TipoAtividade {
        AVALIATIVA,
        COLABORATIVA
    }

    public record DadosAtividadeAvaliativa(
            @NotBlank(message = "O ID da atividade avaliativa é obrigatório.")
            String atividadeAvaliativaId
    ) {
    }

    public record DadosAtividadeColaborativa(
            @NotBlank(message = "O título é obrigatório.")
            String titulo,

            @NotBlank(message = "A descrição é obrigatória.")
            String descricao
    ) {
    }
}