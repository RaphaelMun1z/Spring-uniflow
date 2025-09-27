package io.github.raphaelmuniz.uniflow.dto.req;

import io.github.raphaelmuniz.uniflow.entities.Professor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProfessorRequestDTO {
    private String nome;
    private String email;
    private String senha;
    private String areaAtuacao;
    private String assinaturaId;
    private List<String> gruposId;

    public Professor toModel() {
        return new Professor(null, this.nome, this.email, this.senha, null, null, null, null, null, null, null, this.areaAtuacao);
    }
}
