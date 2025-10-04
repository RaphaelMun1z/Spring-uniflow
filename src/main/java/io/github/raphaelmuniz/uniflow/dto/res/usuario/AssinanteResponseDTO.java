package io.github.raphaelmuniz.uniflow.dto.res.usuario;

import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaUsuario;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;

public record AssinanteResponseDTO(
        String id,
        String nome,
        String email,
        String assinaturaId
) {
    public static AssinanteResponseDTO fromEntity(Assinante assinante) {
        String idAssinatura = assinante.getAssinaturaValida()
                .map(AssinaturaUsuario::getId)
                .orElse(null);

        return new AssinanteResponseDTO(
                assinante.getId(),
                assinante.getNome(),
                assinante.getEmail(),
                idAssinatura
        );
    }
}
