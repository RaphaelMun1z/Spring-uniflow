package io.github.raphaelmuniz.uniflow.dto.req.usuario;

import jakarta.validation.constraints.Size;

public record ProfileUpdateRequestDTO(
        @Size(min = 2, message = "O nome, se fornecido, deve ter ao menos 2 caracteres.")
        String nome,

        String areaAtuacao
) {}