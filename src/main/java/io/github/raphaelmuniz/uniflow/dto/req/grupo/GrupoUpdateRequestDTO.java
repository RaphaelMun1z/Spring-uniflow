package io.github.raphaelmuniz.uniflow.dto.req.grupo;

import jakarta.validation.constraints.Size;

public record GrupoUpdateRequestDTO(
        @Size(min = 1, message = "O título, se fornecido, não pode ser vazio.")
        String titulo,

        @Size(min = 1, message = "A descrição, se fornecida, não pode ser vazia.")
        String descricao
) {}