package io.github.raphaelmuniz.uniflow.dto.req.autorizacao;

import io.github.raphaelmuniz.uniflow.dto.req.RequestData;
import io.github.raphaelmuniz.uniflow.entities.autorizacao.Papel;
import io.github.raphaelmuniz.uniflow.entities.autorizacao.Permissao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class PapelRequestDTO implements RequestData<Papel> {
    @NotBlank
    private String nome;

    @NotNull
    private Set<Permissao> permissoes;

    public Papel toModel(){
        return new Papel(null, nome, permissoes);
    }
}
