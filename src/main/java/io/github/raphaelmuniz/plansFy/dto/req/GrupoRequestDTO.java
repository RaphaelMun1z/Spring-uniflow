package io.github.raphaelmuniz.plansFy.dto.req;

import io.github.raphaelmuniz.plansFy.entities.Grupo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GrupoRequestDTO {
    private String titulo;
    private String descricao;
    private List<String> atividadesId;
    private List<String> inscritosId;

    public Grupo toModel(){
        return new Grupo(null, titulo, descricao, null, null);
    }
}
