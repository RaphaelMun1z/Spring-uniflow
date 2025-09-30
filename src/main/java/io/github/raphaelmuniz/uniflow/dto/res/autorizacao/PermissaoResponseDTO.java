package io.github.raphaelmuniz.uniflow.dto.res.autorizacao;

import io.github.raphaelmuniz.uniflow.entities.autorizacao.Permissao;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PermissaoResponseDTO {
    private String id;
    private String nome;

    public PermissaoResponseDTO(Permissao permissao) {
        this.id = permissao.getId();
        this.nome = permissao.getNome();
    }

    public Permissao toModel() {
        return new Permissao(id, nome);
    }
}
