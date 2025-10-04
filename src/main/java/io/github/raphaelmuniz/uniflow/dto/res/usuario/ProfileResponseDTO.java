package io.github.raphaelmuniz.uniflow.dto.res.usuario;

import io.github.raphaelmuniz.uniflow.dto.res.profile.AssinaturaProfileResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

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

        Set<String> authorities = usuario.getAuthorities() != null
            ? usuario.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toSet())
            : Collections.emptySet();

        return new ProfileResponseDTO(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            authorities,
            assinatura,
            notificacoesNaoLidas
        );
    }
}
