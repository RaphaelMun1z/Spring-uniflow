package io.github.raphaelmuniz.uniflow.entities;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
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
public class Professor extends Assinante implements Serializable {
    @NotBlank(message = "Área de atuação não pode ser vazio/nulo")
    private String areaAtuacao;

    public Professor(String id, String nome, String email, String senha, Set<AssinaturaUsuario> assinaturas, List<Pagamento> pagamentos, Set<AtividadeAssinante> atividadesAssinante, List<AtividadeGrupo> atividadesGrupoPublicadas, Set<InscricaoGrupo> inscricoesGrupos, Set<NotificacaoAssinante> notificacoes, List<Grupo> gruposCriados, String areaAtuacao) {
        super(id, nome, email, senha, assinaturas, pagamentos, atividadesAssinante, atividadesGrupoPublicadas, inscricoesGrupos, notificacoes, gruposCriados);
        this.areaAtuacao = areaAtuacao;
    }
}