package io.github.raphaelmuniz.uniflow.dto.res.usuario;

import io.github.raphaelmuniz.uniflow.dto.res.profile.AssinaturaProfileResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;

import java.util.Set;

public record ProfileResponseDTO(
        String id,
        String nome,
        String email,
        Set<String> papeis,
        AssinaturaProfileResponseDTO assinaturaAtiva,
        int notificacoesNaoLidas
) {
    public static ProfileResponseDTO fromEntity(Usuario usuario, AssinaturaProfileResponseDTO assinatura, int notificacoesNaoLidas) {
        if (usuario == null) {
            return null;
        }

        return new ProfileResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPapel() != null ? Set.of(usuario.getPapel().getNome()) : Set.of(),
                assinatura,
                notificacoesNaoLidas
        );
    }
}
