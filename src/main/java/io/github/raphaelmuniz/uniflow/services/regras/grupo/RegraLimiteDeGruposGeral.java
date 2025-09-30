package io.github.raphaelmuniz.uniflow.services.regras.grupo;

import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaModelo;
import io.github.raphaelmuniz.uniflow.entities.grupo.Grupo;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import io.github.raphaelmuniz.uniflow.exceptions.models.BusinessException;
import io.github.raphaelmuniz.uniflow.repositories.grupo.GrupoRepository;
import org.springframework.stereotype.Component;

@Component
public class RegraLimiteDeGruposGeral implements RegraCriacaoGrupo {
    private static final int LIMITE_MAXIMO_DE_GRUPOS = 20;

    private final GrupoRepository grupoRepository;

    public RegraLimiteDeGruposGeral(GrupoRepository grupoRepository) {
        this.grupoRepository = grupoRepository;
    }

    @Override
    public void verificar(Grupo grupoPai, Assinante criador, AssinaturaModelo plano) {
        if (grupoPai != null) {
            return;
        }

        Integer limiteDeGruposDoPlano = plano.getLimiteDeGrupos();
        int limiteEfetivo = Math.min(limiteDeGruposDoPlano, LIMITE_MAXIMO_DE_GRUPOS);
        long gruposAtuais = grupoRepository.countByAssinanteCriadorGrupoId(criador.getId());

        if (gruposAtuais >= limiteEfetivo) {
            throw new BusinessException("Você atingiu o limite de grupos que pode criar com o seu plano.");
        }
    }
}