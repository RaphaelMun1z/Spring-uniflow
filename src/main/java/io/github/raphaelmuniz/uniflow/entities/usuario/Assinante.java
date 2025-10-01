package io.github.raphaelmuniz.uniflow.entities.usuario;

import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaUsuario;
import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeAvaliativa;
import io.github.raphaelmuniz.uniflow.entities.grupo.Grupo;
import io.github.raphaelmuniz.uniflow.entities.notificacao.NotificacaoAssinante;
import io.github.raphaelmuniz.uniflow.entities.autorizacao.Papel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.*;

@Entity
@Getter
@Setter
@ToString(exclude = {"assinaturas", "atividadesGrupoPublicadas", "notificacoes", "gruposCriados"})
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Assinante extends Usuario implements Serializable {
    public Assinante(String nome, String email, String senha, Set<AssinaturaUsuario> assinaturas, List<AtividadeAvaliativa> atividadesGrupoPublicadas, Set<NotificacaoAssinante> notificacoes, List<Grupo> gruposCriados, Papel papel) {
        super(nome, email, senha, papel);
        this.assinaturas = assinaturas;
        this.atividadesGrupoPublicadas = atividadesGrupoPublicadas;
        this.notificacoes = notificacoes;
        this.gruposCriados = gruposCriados;
    }

    @NotNull(message = "Assinaturas não pode ser nulo")
    @OneToMany(mappedBy = "assinante", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<AssinaturaUsuario> assinaturas = new HashSet<>();

    @NotNull(message = "Atividades Grupo não pode ser nulo")
    @OneToMany(mappedBy = "assinanteCriadorAtividade", cascade = CascadeType.ALL)
    private List<AtividadeAvaliativa> atividadesGrupoPublicadas = new ArrayList<>();

    @OneToMany(mappedBy = "assinanteNotificado", cascade = CascadeType.ALL)
    private Set<NotificacaoAssinante> notificacoes = new HashSet<>();

    @OneToMany(mappedBy = "assinanteCriadorGrupo", cascade = CascadeType.ALL)
    private List<Grupo> gruposCriados = new ArrayList<>();

    @Transient
    public Optional<AssinaturaUsuario> getAssinaturaValida() {
        return this.assinaturas.stream()
                .filter(AssinaturaUsuario::isVigente)
                .findFirst();
    }
}
