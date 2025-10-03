package io.github.raphaelmuniz.uniflow.dto.req.assinatura;

import jakarta.validation.constraints.NotBlank;

public record PagamentoRequestDTO(
        @NotBlank(message = "O ID do assinante é obrigatório.")
        String assinanteId,

        @NotBlank(message = "O ID do modelo de assinatura é obrigatório.")
        String assinaturaModeloId
) {}