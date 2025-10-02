package io.github.raphaelmuniz.uniflow.dto.req.grupo;

import io.github.raphaelmuniz.uniflow.entities.enums.PapelGrupoEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizarPapelMembroGrupoDTO {
    @NotNull(message = "O novo papel não pode ser nulo.")
    private PapelGrupoEnum novoPapelNoGrupo;
}
