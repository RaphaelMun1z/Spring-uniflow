package io.github.raphaelmuniz.uniflow.entities.usuario;

import io.github.raphaelmuniz.uniflow.entities.*;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaUsuario;
import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeAvaliativa;
import io.github.raphaelmuniz.uniflow.entities.grupo.Grupo;
import io.github.raphaelmuniz.uniflow.entities.notificacao.NotificacaoAssinante;
import io.github.raphaelmuniz.uniflow.entities.autorizacao.Papel;
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

    public Professor(String nome, String email, String senha, Set<AssinaturaUsuario> assinaturas, List<Pagamento> pagamentos, List<AtividadeAvaliativa> atividadesGrupoPublicadas, Set<NotificacaoAssinante> notificacoes, List<Grupo> gruposCriados, String areaAtuacao, Papel papel) {
        super(nome, email, senha, assinaturas, pagamentos, atividadesGrupoPublicadas, notificacoes, gruposCriados, papel);
        this.areaAtuacao = areaAtuacao;
    }
}