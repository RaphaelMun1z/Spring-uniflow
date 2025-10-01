package io.github.raphaelmuniz.uniflow.services.regras.grupo;

import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaModelo;
import io.github.raphaelmuniz.uniflow.entities.grupo.Grupo;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import io.github.raphaelmuniz.uniflow.exceptions.models.BusinessException;
import io.github.raphaelmuniz.uniflow.services.regras.grupo.config.RegraCriacaoGrupo;
import org.springframework.stereotype.Component;

@Component
public class RegraLimiteDeSubGrupos implements RegraCriacaoGrupo {
    private static final int LIMITE_MAXIMO_DE_SUBGRUPOS = 10;

    @Override
    public void verificar(Grupo grupoPai, Assinante criador, AssinaturaModelo plano) {
        int limiteEfetivo = Math.min(plano.getLimiteDeSubGrupos(), LIMITE_MAXIMO_DE_SUBGRUPOS);

        if (grupoPai.getSubGrupos().size() >= limiteEfetivo) {
            throw new BusinessException("Limite de subgrupos para o seu plano foi atingido.");
        }
    }
}