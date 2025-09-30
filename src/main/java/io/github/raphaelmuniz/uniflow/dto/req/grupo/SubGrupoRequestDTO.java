package io.github.raphaelmuniz.uniflow.dto.req.grupo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SubGrupoRequestDTO {
    @NotNull
    private String titulo;

    @NotNull
    private String descricao;

    @NotNull
    private String criadorId;

    @NotNull
    private List<String> estudantesInscritosId;
}
