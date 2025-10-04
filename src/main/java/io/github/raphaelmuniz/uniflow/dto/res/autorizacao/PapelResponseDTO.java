package io.github.raphaelmuniz.uniflow.dto.res.autorizacao;

import io.github.raphaelmuniz.uniflow.entities.autorizacao.Papel;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public record PapelResponseDTO(
        String id,
        String nome,
        Set<PermissaoResponseDTO> permissoes
) {
    public static PapelResponseDTO fromEntity(Papel papel) {
        if (papel == null) {
            return null;
        }

        Set<PermissaoResponseDTO> permissoesDTO = papel.getPermissoes() != null
                ? papel.getPermissoes().stream()
                .map(PermissaoResponseDTO::new)
                .collect(Collectors.toSet())
                : Collections.emptySet();

        return new PapelResponseDTO(
                papel.getId(),
                papel.getNome(),
                permissoesDTO
        );
    }
}