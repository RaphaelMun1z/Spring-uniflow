package io.github.raphaelmuniz.uniflow.dto.req;

import io.github.raphaelmuniz.uniflow.entities.usuario.Professor;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

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

    public Professor toModel() {
        return new Professor(this.nome, this.email, this.senha, null, null, null, null, null, this.areaAtuacao, null);
    }
}
