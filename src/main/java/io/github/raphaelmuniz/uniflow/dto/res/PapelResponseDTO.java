package io.github.raphaelmuniz.uniflow.dto.res;

import io.github.raphaelmuniz.uniflow.entities.Disciplina;
import io.github.raphaelmuniz.uniflow.entities.Papel;
import io.github.raphaelmuniz.uniflow.entities.Permissao;
import io.github.raphaelmuniz.uniflow.entities.embeddables.PeriodoLetivo;
import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
