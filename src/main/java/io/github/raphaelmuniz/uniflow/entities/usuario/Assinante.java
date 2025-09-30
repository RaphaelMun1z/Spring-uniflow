package io.github.raphaelmuniz.uniflow.entities.usuario;

import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaUsuario;
import io.github.raphaelmuniz.uniflow.entities.assinatura.Pagamento;
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
@ToString
@EqualsAndHashCode(callSuper = true, exclude = {"assinaturas", "atividadesGrupoPublicadas", "notificacoes"})
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Assinante extends Usuario implements Serializable {
    public Assinante(String nome, String email, String senha, Set<AssinaturaUsuario> assinaturas, List<Pagamento> pagamentos, List<AtividadeAvaliativa> atividadesGrupoPublicadas, Set<NotificacaoAssinante> notificacoes, List<Grupo> gruposCriados, Papel papel) {
        super(nome, email, senha, papel);
        this.assinaturas = assinaturas;
        this.pagamentos = pagamentos;
        this.atividadesGrupoPublicadas = atividadesGrupoPublicadas;
        this.notificacoes = notificacoes;
        this.gruposCriados = gruposCriados;
    }

    @NotNull(message = "Assinaturas não pode ser nulo")
    @OneToMany(mappedBy = "assinante", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<AssinaturaUsuario> assinaturas = new HashSet<>();

    @NotNull(message = "Pagamentos não pode ser nulo")
    @OneToMany(mappedBy = "assinantePagador", cascade = CascadeType.ALL)
    private List<Pagamento> pagamentos = new ArrayList<>();

    @NotNull(message = "Atividades Grupo não pode ser nulo")
    @OneToMany(mappedBy = "assinanteCriadorAtividade", cascade = CascadeType.ALL)
    private List<AtividadeAvaliativa> atividadesGrupoPublicadas = new ArrayList<>();

    @OneToMany(mappedBy = "assinanteNotificado", cascade = CascadeType.ALL)
    private Set<NotificacaoAssinante> notificacoes = new HashSet<>();

    @OneToMany(mappedBy = "assinanteCriadorGrupo", cascade = CascadeType.ALL)
    private List<Grupo> gruposCriados;

    public Optional<AssinaturaUsuario> getAssinaturaValida() {
        return assinaturas.stream()
                .filter(AssinaturaUsuario::getStatus)
                .findFirst();
    }
}
