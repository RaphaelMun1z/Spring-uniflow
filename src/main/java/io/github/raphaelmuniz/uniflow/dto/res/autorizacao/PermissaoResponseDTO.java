package io.github.raphaelmuniz.uniflow.dto.res.autorizacao;

import io.github.raphaelmuniz.uniflow.entities.autorizacao.Permissao;

public record PermissaoResponseDTO(
        String id,
        String nome
) {
    public PermissaoResponseDTO(Permissao permissao) {
        this(permissao.getId(), permissao.getNome());
    }
}