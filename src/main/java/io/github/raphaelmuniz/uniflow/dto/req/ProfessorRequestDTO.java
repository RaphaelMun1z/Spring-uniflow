package io.github.raphaelmuniz.uniflow.dto.req;

import io.github.raphaelmuniz.uniflow.entities.Professor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProfessorRequestDTO {
    @NotBlank
    private String nome;

    @NotBlank
    private String email;

    @NotBlank
    private String senha;

    @NotBlank
    private String areaAtuacao;

    @NotBlank
    private String assinaturaId;

    @NotNull
    private List<String> gruposId;

    public Professor toModel() {
        return new Professor(this.nome, this.email, this.senha, null, null, null, null, null, null, null, this.areaAtuacao, null);
    }
}
