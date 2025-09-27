package io.github.raphaelmuniz.uniflow.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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

    public Estudante(String id, String nome, String email, String senha, Set<AssinaturaUsuario> assinaturas, List<Pagamento> pagamentos, Set<AtividadeAssinante> atividadesAssinante, List<AtividadeGrupo> atividadesGrupoPublicadas, Set<InscricaoGrupo> inscricoesGrupos, Set<NotificacaoAssinante> notificacoes, List<Grupo> gruposCriados, Integer periodo) {
        super(id, nome, email, senha, assinaturas, pagamentos, atividadesAssinante, atividadesGrupoPublicadas, inscricoesGrupos, notificacoes, gruposCriados);
        this.periodo = periodo;
    }
}
