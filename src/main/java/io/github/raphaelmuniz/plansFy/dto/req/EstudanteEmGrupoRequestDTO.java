package io.github.raphaelmuniz.plansFy.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EstudanteEmGrupoRequestDTO {
    @NotNull
    private String alunoId;

    @NotNull
    private String grupoId;
}
