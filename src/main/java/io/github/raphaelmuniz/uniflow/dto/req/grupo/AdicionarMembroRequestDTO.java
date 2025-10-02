package io.github.raphaelmuniz.uniflow.dto.req.grupo;

import jakarta.validation.constraints.NotBlank;

public record AdicionarMembroRequestDTO(
        @NotBlank(message = "O ID do usuário a ser adicionado não pode ser vazio.")
        String usuarioId
) {
}