package io.github.raphaelmuniz.uniflow.dto.res;

import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaUsuario;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssinanteResponseDTO {
    private String id;
    private String nome;
    private String email;
    private String assinaturaId;

    public AssinanteResponseDTO(Assinante assinante) {
        this.id = assinante.getId();
        this.nome = assinante.getNome();
        this.email = assinante.getEmail();
        this.assinaturaId = assinante.getAssinaturaValida().map(AssinaturaUsuario::getId).orElse(null);
    }
}
