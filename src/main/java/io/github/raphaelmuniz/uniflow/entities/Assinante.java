package io.github.raphaelmuniz.uniflow.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Assinante extends Usuario implements Serializable {
    @NotNull(message = "Assinaturas não pode ser nulo")
    @OneToMany(mappedBy = "assinante", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<AssinaturaUsuario> assinaturas;

    @NotNull(message = "Pagamentos não pode ser nulo")
    @OneToMany(mappedBy = "assinantePagador", cascade = CascadeType.ALL)
    private List<Pagamento> pagamentos = new ArrayList<>();

    @NotNull(message = "Atividades não pode ser nulo")
    @OneToMany(mappedBy = "assinanteDono", cascade = CascadeType.ALL)
    private List<AtividadeAssinante> atividadesAssinante = new ArrayList<>();

    @NotNull(message = "Atividades Grupo não pode ser nulo")
    @OneToMany(mappedBy = "criadorAtividade", cascade = CascadeType.ALL)
    private List<AtividadeGrupo> atividadesGrupoPublicadas = new ArrayList<>();

    @NotNull(message = "Inscrições Grupos não pode ser nulo")
    @OneToMany(mappedBy = "membro", cascade = CascadeType.ALL)
    private Set<InscricaoGrupo> inscricoesGrupos = new HashSet<>();

    @OneToMany(mappedBy = "assinante", cascade = CascadeType.ALL)
    private Set<NotificacaoAssinante> notificacoes = new HashSet<>();
}
