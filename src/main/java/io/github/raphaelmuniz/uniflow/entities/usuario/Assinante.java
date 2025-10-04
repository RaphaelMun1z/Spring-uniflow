package io.github.raphaelmuniz.uniflow.entities.usuario;

import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaUsuario;
import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeAvaliativa;
import io.github.raphaelmuniz.uniflow.entities.grupo.Grupo;
import io.github.raphaelmuniz.uniflow.entities.grupo.InscricaoGrupo;
import io.github.raphaelmuniz.uniflow.entities.notificacao.NotificacaoAssinante;
import io.github.raphaelmuniz.uniflow.entities.autorizacao.Papel;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"assinaturas", "atividadesCriadas", "notificacoes", "gruposCriados", "inscricoes"}) // Ajustado
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Assinante extends Usuario implements Serializable {

    @OneToMany(mappedBy = "assinante", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<AssinaturaUsuario> assinaturas = new HashSet<>();

    @OneToMany(mappedBy = "assinanteCriadorAtividade")
    private List<AtividadeAvaliativa> atividadesCriadas = new ArrayList<>();

    @OneToMany(mappedBy = "destinatario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<NotificacaoAssinante> notificacoes = new HashSet<>();

    @OneToMany(mappedBy = "membro", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<InscricaoGrupo> inscricoes = new HashSet<>();

    @OneToMany(mappedBy = "assinanteCriadorGrupo")
    private List<Grupo> gruposCriados = new ArrayList<>();

    @Transient
    public Optional<AssinaturaUsuario> getAssinaturaValida() {
        if (this.assinaturas == null) {
            return Optional.empty();
        }
        return this.assinaturas.stream()
                .filter(AssinaturaUsuario::isVigente)
                .findFirst();
    }
}