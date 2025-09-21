package io.github.raphaelmuniz.plansFy.dto.res;

import io.github.raphaelmuniz.plansFy.entities.AssinaturaUsuario;
import io.github.raphaelmuniz.plansFy.entities.AtividadeCopia;
import io.github.raphaelmuniz.plansFy.entities.InscricaoGrupo;
import io.github.raphaelmuniz.plansFy.entities.Professor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
