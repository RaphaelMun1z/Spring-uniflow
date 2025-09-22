package io.github.raphaelmuniz.uniflow.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InscricaoGrupoRequestDTO {
    @NotNull
    private String alunoId;

    @NotNull
    private String grupoId;
}
