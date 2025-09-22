package io.github.raphaelmuniz.plansFy.dto.res;

import io.github.raphaelmuniz.plansFy.entities.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssinanteResponseDTO {
    private String id;
    private String nome;
    private String email;
    private String assinaturaId;
    private List<String> atividadesId;
    private List<String> gruposId;

    public AssinanteResponseDTO(Assinante assinante) {
        this.id = assinante.getId();
        this.nome = assinante.getNome();
        this.email = assinante.getEmail();
        this.assinaturaId = assinante.getAssinaturaUsuario().getId();
        this.atividadesId = assinante.getAtividades().stream().map(AtividadeCopia::getId).toList();
        this.gruposId = assinante.getGrupos().stream().map(g -> {
            return g.getGrupo().getId();
        }).toList();
    }
}
