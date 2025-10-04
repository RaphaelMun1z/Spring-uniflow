package io.github.raphaelmuniz.uniflow.dto.req.assinatura;

import jakarta.validation.constraints.NotBlank;

public record AssinaturaUsuarioRequestDTO(
        @NotBlank(message = "O ID do usuário (assinante) é obrigatório.")
        String usuarioId,

        @NotBlank(message = "O ID do modelo de assinatura é obrigatório.")
        String modeloId
) {
}