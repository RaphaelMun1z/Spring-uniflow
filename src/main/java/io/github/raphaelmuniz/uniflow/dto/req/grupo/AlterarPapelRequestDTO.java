package io.github.raphaelmuniz.uniflow.dto.req.grupo;

import io.github.raphaelmuniz.uniflow.entities.enums.PapelGrupoEnum;
import jakarta.validation.constraints.NotNull;

public record AlterarPapelRequestDTO(
        @NotNull(message = "O novo papel não pode ser nulo.")
        PapelGrupoEnum novoPapel
) {}