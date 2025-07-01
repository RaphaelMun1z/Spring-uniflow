package io.github.raphaelmuniz.plansFy.dto.req;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AdicionarMembrosGrupoDTO {
    @NotEmpty(message = "A lista de integrantes não pode ser vazia.")
    private List<String> integrantesId;

    @NotNull(message = "O ID do grupo não pode ser nulo.")
    private String grupoId;
}
