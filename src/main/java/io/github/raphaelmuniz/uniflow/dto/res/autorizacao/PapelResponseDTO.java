package io.github.raphaelmuniz.uniflow.dto.res.autorizacao;

import io.github.raphaelmuniz.uniflow.entities.autorizacao.Papel;
import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class PapelResponseDTO {
    private final String id;
    private final String nome;
    private final Set<PermissaoResponseDTO> permissoes;

    public PapelResponseDTO(Papel papel) {
        this.id = papel.getId();
        this.nome = papel.getNome();
        this.permissoes = papel.getPermissoes().stream()
                .map(PermissaoResponseDTO::new)
                .collect(Collectors.toSet());
    }
}