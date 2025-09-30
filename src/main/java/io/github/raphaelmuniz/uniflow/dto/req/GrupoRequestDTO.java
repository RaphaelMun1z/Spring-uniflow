package io.github.raphaelmuniz.uniflow.dto.req;

import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeAvaliativa;
import io.github.raphaelmuniz.uniflow.entities.grupo.Grupo;
import io.github.raphaelmuniz.uniflow.entities.enums.TipoGrupoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GrupoRequestDTO {
    @NotBlank
    private String titulo;

    @NotBlank
    private String descricao;

    @NotNull
    private TipoGrupoEnum tipoGrupo;

    @NotNull
    private List<AtividadeAvaliativa> atividadesGrupo;

    @NotNull
    private List<String> estudantesInscritosId;

    @NotNull
    private String criadorId;

    public Grupo toModel() {
        return new Grupo(null, titulo, descricao, tipoGrupo, null, null, null);
    }
}
