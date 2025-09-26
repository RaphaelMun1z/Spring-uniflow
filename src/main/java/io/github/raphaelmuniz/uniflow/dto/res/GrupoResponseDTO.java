package io.github.raphaelmuniz.uniflow.dto.res;

import io.github.raphaelmuniz.uniflow.entities.*;
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
    private List<String> inscritosId;
    private String criadorId;

    public GrupoResponseDTO(Grupo grupo) {
        this.id = grupo.getId();
        this.descricao = grupo.getDescricao();
        this.titulo = grupo.getTitulo();
        this.atividadesId = grupo.getAtividadesPublicadas().stream().map(AtividadeGrupo::getId).toList();
        this.inscritosId = grupo.getInscricoes().stream().map(ig -> {
            return ig.getMembro().getId();
        }).toList();
        this.criadorId = grupo.getCriador().getId();
    }
}
