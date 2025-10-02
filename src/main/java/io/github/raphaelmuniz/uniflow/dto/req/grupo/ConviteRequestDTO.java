package io.github.raphaelmuniz.uniflow.dto.req.grupo;

import jakarta.validation.constraints.NotBlank;

public record ConviteRequestDTO(
        @NotBlank(message = "O código do convite não pode ser vazio.")
        String codigoConvite
) {}