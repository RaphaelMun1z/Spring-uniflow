package io.github.raphaelmuniz.uniflow.dto.req;

import io.github.raphaelmuniz.uniflow.entities.Papel;
import io.github.raphaelmuniz.uniflow.entities.Permissao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class PermissaoRequestDTO {
    @NotBlank
    private String nome;

    public Permissao toModel() {
        return new Permissao(null, nome);
    }
}
