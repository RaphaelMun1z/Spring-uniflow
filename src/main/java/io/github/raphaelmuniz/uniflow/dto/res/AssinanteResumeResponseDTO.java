package io.github.raphaelmuniz.uniflow.dto.res;

import io.github.raphaelmuniz.uniflow.entities.Assinante;
import io.github.raphaelmuniz.uniflow.entities.AssinaturaUsuario;
import io.github.raphaelmuniz.uniflow.entities.AtividadeAssinante;
import io.github.raphaelmuniz.uniflow.entities.InscricaoGrupo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssinanteResumeResponseDTO {
    private String id;
    private String nome;
    private String email;

    public AssinanteResumeResponseDTO(Assinante assinante) {
        this.id = assinante.getId();
        this.nome = assinante.getNome();
        this.email = assinante.getEmail();
    }
}
