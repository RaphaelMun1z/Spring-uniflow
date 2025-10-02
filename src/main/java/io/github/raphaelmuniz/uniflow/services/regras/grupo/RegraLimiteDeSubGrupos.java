package io.github.raphaelmuniz.uniflow.services.regras.grupo;

import io.github.raphaelmuniz.uniflow.exceptions.models.BusinessException;
import io.github.raphaelmuniz.uniflow.services.regras.grupo.config.ContextoCriacaoGrupo;
import io.github.raphaelmuniz.uniflow.services.regras.grupo.config.RegraCriacaoGrupo;
import org.springframework.stereotype.Component;

@Component
public class RegraLimiteDeSubGrupos implements RegraCriacaoGrupo {
    private static final int LIMITE_MAXIMO_DE_SUBGRUPOS = 10;

    @Override
    public void verificar(ContextoCriacaoGrupo contexto) {
        int limiteEfetivo = Math.min(contexto.planoDoCriador().getLimiteDeSubGrupos(), LIMITE_MAXIMO_DE_SUBGRUPOS);

        if (contexto.grupoPai().getSubGrupos().size() >= limiteEfetivo) {
            throw new BusinessException("Limite de subgrupos para o seu plano foi atingido.");
        }
    }
}