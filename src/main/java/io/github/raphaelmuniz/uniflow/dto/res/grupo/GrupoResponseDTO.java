package io.github.raphaelmuniz.uniflow.dto.res.grupo;

import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeAvaliativa;
import io.github.raphaelmuniz.uniflow.entities.grupo.Grupo;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public record GrupoResponseDTO(
        String id,
        String titulo,
        String descricao,
        String criadorId,
        List<String> idsAtividades,
        List<String> idsMembros
) {

    public static GrupoResponseDTO fromEntity(Grupo grupo) {
        if (grupo == null) {
            return null;
        }

        List<String> idsAtividades = grupo.getAtividadesPublicadas() != null
                ? grupo.getAtividadesPublicadas().stream()
                .map(AtividadeAvaliativa::getId)
                .collect(Collectors.toList())
                : Collections.emptyList();

        List<String> idsMembros = grupo.getInscricoes() != null
                ? grupo.getInscricoes().stream()
                .map(inscricao -> inscricao.getMembro().getId())
                .collect(Collectors.toList())
                : Collections.emptyList();

        return new GrupoResponseDTO(
                grupo.getId(),
                grupo.getTitulo(),
                grupo.getDescricao(),
                grupo.getAssinanteCriadorGrupo().getId(),
                idsAtividades,
                idsMembros
        );
    }
}