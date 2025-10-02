package io.github.raphaelmuniz.uniflow.dto.res.grupo;

import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeAvaliativa;
import io.github.raphaelmuniz.uniflow.entities.grupo.Grupo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GrupoResponseDTO {
    private String id;
    private String descricao;
    private String titulo;
    private List<String> atividadesId;
    private List<String> estudantesMembrosId;
    private String criadorId;

    public GrupoResponseDTO(Grupo grupo) {
        this.id = grupo.getId();
        this.descricao = grupo.getDescricao();
        this.titulo = grupo.getTitulo();
        this.atividadesId = grupo.getAtividadesPublicadas().stream().map(AtividadeAvaliativa::getId).toList();
        this.estudantesMembrosId = grupo.getInscricoes().stream().map(ig -> ig.getMembro().getId()).toList();
        this.criadorId = grupo.getAssinanteCriadorGrupo().getId();
    }
}
