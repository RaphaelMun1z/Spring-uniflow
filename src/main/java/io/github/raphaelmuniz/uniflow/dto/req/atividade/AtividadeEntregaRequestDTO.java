package io.github.raphaelmuniz.uniflow.dto.req.atividade;

import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AtividadeEntregaRequestDTO(
        @NotNull(message = "O novo status da entrega é obrigatório.")
        StatusEntregaEnum status,

        String textoResposta,

        List<String> anexos
) {
}
