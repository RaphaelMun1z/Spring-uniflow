package io.github.raphaelmuniz.uniflow.dto.res;

import io.github.raphaelmuniz.uniflow.entities.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfessorResponseDTO {
    private String id;
    private String nome;
    private String email;
    private String areaAtuacao;
    private String assinaturaId;

    public ProfessorResponseDTO(Professor professor) {
        this.id = professor.getId();
        this.nome = professor.getNome();
        this.email = professor.getEmail();
        this.areaAtuacao = professor.getAreaAtuacao();
        this.assinaturaId = professor.getAssinaturaValida().map(AssinaturaUsuario::getId).orElse(null);
    }

    public Professor toModel() {
        return new Professor(this.nome, this.email, null, null, null, null, null, null, this.areaAtuacao, null);
    }
}
