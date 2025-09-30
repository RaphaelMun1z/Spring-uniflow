package io.github.raphaelmuniz.uniflow.entities.usuario;

import io.github.raphaelmuniz.uniflow.entities.*;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaUsuario;
import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeEntrega;
import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeAvaliativa;
import io.github.raphaelmuniz.uniflow.entities.grupo.Grupo;
import io.github.raphaelmuniz.uniflow.entities.grupo.InscricaoGrupo;
import io.github.raphaelmuniz.uniflow.entities.notificacao.NotificacaoAssinante;
import io.github.raphaelmuniz.uniflow.entities.autorizacao.Papel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Estudante extends Assinante implements Serializable {
    @NotNull(message = "Período não pode ser nulo")
    private Integer periodo;

    @NotNull(message = "Inscrições Grupos não pode ser nulo")
    @OneToMany(mappedBy = "estudanteMembro", cascade = CascadeType.ALL)
    private Set<InscricaoGrupo> inscricoesGrupos = new HashSet<>();

    @NotNull(message = "Atividades não pode ser nulo")
    @OneToMany(mappedBy = "estudanteDono", cascade = CascadeType.ALL)
    private Set<AtividadeEntrega> atividadesEstudante = new HashSet<>();

    public Estudante(String nome, String email, String senha, Set<AssinaturaUsuario> assinaturas, List<Pagamento> pagamentos, Set<AtividadeEntrega> atividadesEstudante, List<AtividadeAvaliativa> atividadesGrupoPublicadas, Set<NotificacaoAssinante> notificacoes, List<Grupo> gruposCriados, Papel papel, Integer periodo, Set<InscricaoGrupo> inscricoesGrupos) {
        super(nome, email, senha, assinaturas, pagamentos, atividadesGrupoPublicadas, notificacoes, gruposCriados, papel);
        this.periodo = periodo;
        this.inscricoesGrupos = inscricoesGrupos;
        this.atividadesEstudante = atividadesEstudante;
    }
}
