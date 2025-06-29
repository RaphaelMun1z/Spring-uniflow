package io.github.raphaelmuniz.plansFy.dto.res;

import io.github.raphaelmuniz.plansFy.entities.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class GrupoResponseDTO {
    private String id;
    private String descricao;
    private String titulo;
    private List<String> atividadesId;
    private List<String> inscritosId;

    public GrupoResponseDTO(Grupo grupo) {
        this.id = grupo.getId();
        this.descricao = grupo.getDescricao();
        this.titulo = grupo.getTitulo();
        this.atividadesId = grupo.getAtividades().stream().map(AtividadeCopia::getId).toList();
        this.inscritosId = grupo.getInscritos().stream().map(ins -> { return ins.getInscrito().getId(); }).toList();
    }
}
