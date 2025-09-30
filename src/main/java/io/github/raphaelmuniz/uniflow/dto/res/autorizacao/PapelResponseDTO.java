package io.github.raphaelmuniz.uniflow.dto.res.autorizacao;

import io.github.raphaelmuniz.uniflow.entities.autorizacao.Papel;
import io.github.raphaelmuniz.uniflow.entities.autorizacao.Permissao;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class PapelResponseDTO {
    private String id;
    private String nome;
    private Set<Permissao> permissoes;

    public PapelResponseDTO(Papel papel) {
        this.id = papel.getId();
        this.nome = papel.getNome();
        this.permissoes = papel.getPermissoes();
    }

    public Papel toModel() {
        return new Papel(id, nome, permissoes);
    }
}
