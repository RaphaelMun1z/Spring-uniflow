package io.github.raphaelmuniz.uniflow.dto.req.atividade;

import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import jakarta.validation.constraints.NotNull;

public record TarefaStatusUpdateRequestDTO(
        @NotNull(message = "O novo status é obrigatório.")
        StatusEntregaEnum novoStatus
) {}