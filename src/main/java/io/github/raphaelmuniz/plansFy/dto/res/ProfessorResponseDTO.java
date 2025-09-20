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
    private AssinaturaUsuario assinatura;
    private List<AtividadeCopia> atividades;
    private Set<InscricaoGrupo> grupos;

    public ProfessorResponseDTO(Professor professor) {
        this.id = professor.getId();
        this.nome = professor.getNome();
        this.email = professor.getEmail();
        this.areaAtuacao = professor.getAreaAtuacao();
        this.assinatura = professor.getAssinaturaUsuario();
        this.atividades = professor.getAtividades();
        this.grupos = professor.getGrupos();
    }

    public Professor toModel() {
        return new Professor(id, nome, email, assinatura, atividades, grupos, areaAtuacao);
    }
}
