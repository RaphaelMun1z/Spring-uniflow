package io.github.raphaelmuniz.uniflow.dto.req.autorizacao;

import io.github.raphaelmuniz.uniflow.dto.req.RequestData;
import io.github.raphaelmuniz.uniflow.entities.autorizacao.Permissao;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PermissaoRequestDTO implements RequestData<Permissao> {
    @NotBlank
    private String nome;

    public Permissao toModel() {
        return new Permissao(null, nome);
    }
}
