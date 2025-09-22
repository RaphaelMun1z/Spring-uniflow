package io.github.raphaelmuniz.uniflow.dto.res;

import io.github.raphaelmuniz.uniflow.entities.AtividadeCopia;
import io.github.raphaelmuniz.uniflow.entities.InscricaoGrupo;
import io.github.raphaelmuniz.uniflow.entities.Professor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProfessorResponseDTO {
    private String id;
    private String nome;
    private String email;
    private String areaAtuacao;
    private String assinaturaId;
    private List<String> atividadesId;
    private List<String> gruposId;

    public ProfessorResponseDTO(Professor professor) {
        this.id = professor.getId();
        this.nome = professor.getNome();
        this.email = professor.getEmail();
        this.areaAtuacao = professor.getAreaAtuacao();
        this.assinaturaId = professor.getAssinaturaUsuario().getId();
        this.atividadesId = professor.getAtividades().stream().map(AtividadeCopia::getId).toList();
        this.gruposId = professor.getGrupos().stream().map(InscricaoGrupo::getId).toList();
    }

    public Professor toModel() {
        return new Professor(id, nome, email, null, null, null, areaAtuacao);
    }
}
