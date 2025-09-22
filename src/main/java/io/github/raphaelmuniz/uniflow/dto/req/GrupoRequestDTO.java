package io.github.raphaelmuniz.uniflow.dto.req;

import io.github.raphaelmuniz.uniflow.entities.Grupo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
