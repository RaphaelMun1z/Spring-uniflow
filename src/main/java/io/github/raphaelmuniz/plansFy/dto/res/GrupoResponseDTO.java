package io.github.raphaelmuniz.plansFy.dto.res;

import io.github.raphaelmuniz.plansFy.entities.AtividadeCopia;
import io.github.raphaelmuniz.plansFy.entities.Estudante;
import io.github.raphaelmuniz.plansFy.entities.Grupo;
import io.github.raphaelmuniz.plansFy.entities.InscricaoGrupo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class GrupoResponseDTO {
    private String id;
    private String descricao;
    private String titulo;
    private List<AtividadeCopia> atividades;
    private Set<InscricaoGrupo> inscritos;

    public GrupoResponseDTO(Grupo grupo) {
        this.id = grupo.getId();
        this.descricao = grupo.getDescricao();
        this.titulo = grupo.getTitulo();
        this.atividades = grupo.getAtividades();
        this.inscritos = grupo.getInscritos();
    }

    public Grupo toModel() {
        return new Grupo(id, titulo, descricao, atividades, inscritos);
    }
}
