package io.github.raphaelmuniz.plansFy.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdicionarMembroGrupoDTO {
    @NotNull(message = "O ID do integrante não pode ser nulo.")
    private String integranteId;

    @NotNull(message = "O ID do grupo não pode ser nulo.")
    private String grupoId;

    @NotNull(message = "O papel no grupo não pode ser nulo.")
    private String papel;
}
