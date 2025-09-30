package io.github.raphaelmuniz.uniflow.entities;

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
    public Assinante(String nome, String email, String senha, Set<AssinaturaUsuario> assinaturas, List<Pagamento> pagamentos, List<AtividadeGrupo> atividadesGrupoPublicadas, Set<NotificacaoAssinante> notificacoes, List<Grupo> gruposCriados, Papel papel) {
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
    @OneToMany(mappedBy = "criadorAtividade", cascade = CascadeType.ALL)
    private List<AtividadeGrupo> atividadesGrupoPublicadas = new ArrayList<>();

    @OneToMany(mappedBy = "assinante", cascade = CascadeType.ALL)
    private Set<NotificacaoAssinante> notificacoes = new HashSet<>();

    @OneToMany(mappedBy = "criador", cascade = CascadeType.ALL)
    private List<Grupo> gruposCriados;

    public Optional<AssinaturaUsuario> getAssinaturaValida() {
        return assinaturas.stream()
                .filter(AssinaturaUsuario::getStatus)
                .findFirst();
    }
}
