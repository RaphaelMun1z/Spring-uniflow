package io.github.raphaelmuniz.uniflow.dto.res.profile;

import io.github.raphaelmuniz.uniflow.entities.grupo.InscricaoGrupo;
import io.github.raphaelmuniz.uniflow.entities.enums.PapelGrupoEnum;

import java.time.LocalDateTime;

public record GruposProfileResponseDTO(
        String grupoId,
        String descricao,
        String titulo,
        String criadorNome,
        String criadorId,
        LocalDateTime dataIngresso,
        PapelGrupoEnum papelNoGrupo
) {
    public static GruposProfileResponseDTO fromEntity(InscricaoGrupo inscricao) {
        if (inscricao == null || inscricao.getGrupo() == null) {
            return null;
        }

        return new GruposProfileResponseDTO(
                inscricao.getGrupo().getId(),
                inscricao.getGrupo().getDescricao(),
                inscricao.getGrupo().getTitulo(),
                inscricao.getGrupo().getAssinanteCriadorGrupo() != null
                        ? inscricao.getGrupo().getAssinanteCriadorGrupo().getNome()
                        : null,
                inscricao.getGrupo().getAssinanteCriadorGrupo() != null
                        ? inscricao.getGrupo().getAssinanteCriadorGrupo().getId()
                        : null,
                inscricao.getDataEntrada(),
                inscricao.getPapelNoGrupo()
        );
    }
}
