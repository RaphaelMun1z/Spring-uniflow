package io.github.raphaelmuniz.uniflow.dto.req;

import io.github.raphaelmuniz.uniflow.entities.autorizacao.Papel;
import io.github.raphaelmuniz.uniflow.entities.autorizacao.Permissao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class PapelRequestDTO {
    @NotBlank
    private String nome;

    @NotNull
    private Set<Permissao> permissoes;

    public Papel toModel(){
        return new Papel(null, nome, permissoes);
    }
}
