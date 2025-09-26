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
@EqualsAndHashCode(callSuper = true, exclude = {"assinaturas", "atividadesAssinante", "atividadesGrupoPublicadas", "inscricoesGrupos", "notificacoes"})
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Assinante extends Usuario implements Serializable {
    public Assinante(String id, String nome, String email, Set<AssinaturaUsuario> assinaturas, List<Pagamento> pagamentos, Set<AtividadeAssinante> atividadesAssinante, List<AtividadeGrupo> atividadesGrupoPublicadas, Set<InscricaoGrupo> inscricoesGrupos, Set<NotificacaoAssinante> notificacoes) {
        super(id, nome, email);
        this.assinaturas = assinaturas;
        this.pagamentos = pagamentos;
        this.atividadesAssinante = atividadesAssinante;
        this.atividadesGrupoPublicadas = atividadesGrupoPublicadas;
        this.inscricoesGrupos = inscricoesGrupos;
        this.notificacoes = notificacoes;
    }

    @NotNull(message = "Assinaturas não pode ser nulo")
    @OneToMany(mappedBy = "assinante", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<AssinaturaUsuario> assinaturas = new HashSet<>();

    @NotNull(message = "Pagamentos não pode ser nulo")
    @OneToMany(mappedBy = "assinantePagador", cascade = CascadeType.ALL)
    private List<Pagamento> pagamentos = new ArrayList<>();

    @NotNull(message = "Atividades não pode ser nulo")
    @OneToMany(mappedBy = "assinanteDono", cascade = CascadeType.ALL)
    private Set<AtividadeAssinante> atividadesAssinante = new HashSet<>();

    @NotNull(message = "Atividades Grupo não pode ser nulo")
    @OneToMany(mappedBy = "criadorAtividade", cascade = CascadeType.ALL)
    private List<AtividadeGrupo> atividadesGrupoPublicadas = new ArrayList<>();

    @NotNull(message = "Inscrições Grupos não pode ser nulo")
    @OneToMany(mappedBy = "membro", cascade = CascadeType.ALL)
    private Set<InscricaoGrupo> inscricoesGrupos = new HashSet<>();

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
