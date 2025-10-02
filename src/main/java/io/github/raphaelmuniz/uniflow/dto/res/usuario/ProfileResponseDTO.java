package io.github.raphaelmuniz.uniflow.dto.res.usuario;

import io.github.raphaelmuniz.uniflow.dto.res.profile.AssinaturaProfileResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ProfileResponseDTO {
    private String id;
    private String nome;
    private String email;
    private Set<String> papeis;
    private AssinaturaProfileResponseDTO assinaturaAtiva;
    private int notificacoesNaoLidas;

    public ProfileResponseDTO(Usuario usuario, AssinaturaProfileResponseDTO assinatura, int notificacoesNaoLidas) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.papeis = Set.of(usuario.getPapel().getNome());
        this.assinaturaAtiva = assinatura;
        this.notificacoesNaoLidas = notificacoesNaoLidas;
    }
}