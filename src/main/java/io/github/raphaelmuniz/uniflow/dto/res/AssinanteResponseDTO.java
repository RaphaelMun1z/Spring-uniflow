package io.github.raphaelmuniz.uniflow.dto.res;

import io.github.raphaelmuniz.uniflow.entities.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssinanteResponseDTO {
    private String id;
    private String nome;
    private String email;
    private String assinaturaId;
    private List<String> atividadesId;
    private List<String> inscricoesGrupoId;

    public AssinanteResponseDTO(Assinante assinante) {
        this.id = assinante.getId();
        this.nome = assinante.getNome();
        this.email = assinante.getEmail();
        this.assinaturaId = assinante.getAssinaturaValida().map(AssinaturaUsuario::getId).orElse(null);
        this.atividadesId = assinante.getAtividadesAssinante().stream().map(AtividadeAssinante::getId).toList();
        this.inscricoesGrupoId = assinante.getInscricoesGrupos().stream().map(InscricaoGrupo::getId).toList();
    }
}
