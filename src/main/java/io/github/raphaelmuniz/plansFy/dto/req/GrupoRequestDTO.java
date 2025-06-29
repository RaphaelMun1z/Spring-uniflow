package io.github.raphaelmuniz.plansFy.dto.req;

import io.github.raphaelmuniz.plansFy.entities.Grupo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GrupoRequestDTO {
    @NotBlank
    private String titulo;

    @NotBlank
    private String descricao;

    @NotNull
    private List<String> atividadesId;

    @NotNull
    private List<String> inscritosId;

    public Grupo toModel(){
        return new Grupo(null, titulo, descricao, null, null);
    }
}
