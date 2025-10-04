package io.github.raphaelmuniz.uniflow.dto.res.grupo;

import io.github.raphaelmuniz.uniflow.entities.enums.PapelGrupoEnum;
import io.github.raphaelmuniz.uniflow.entities.grupo.InscricaoGrupo;

public record MembroGrupoResponseDTO(
        String id,
        String nome,
        PapelGrupoEnum papelNoGrupo
) {
    public static MembroGrupoResponseDTO fromEntity(InscricaoGrupo inscricao) {
        if (inscricao == null || inscricao.getMembro() == null) {
            return null;
        }
        return new MembroGrupoResponseDTO(
                inscricao.getMembro().getId(),
                inscricao.getMembro().getNome(),
                inscricao.getPapelNoGrupo()
        );
    }
}