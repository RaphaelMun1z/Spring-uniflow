package io.github.raphaelmuniz.uniflow.services.regras.grupo.config;

import io.github.raphaelmuniz.uniflow.services.regras.grupo.RegraLimiteDeGruposGeral;
import io.github.raphaelmuniz.uniflow.services.regras.grupo.RegraLimiteDeSubGrupos;
import io.github.raphaelmuniz.uniflow.services.regras.grupo.RegraProfundidadeMaxima;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RegrasGrupoConfig {
    @Bean
    @Qualifier("regrasCriacaoGrupoPadrao")
    public List<RegraCriacaoGrupo> regrasCriacaoGrupoPadrao(
            RegraLimiteDeGruposGeral regraLimiteGrupos
    ) {
        return List.of(regraLimiteGrupos);
    }

    @Bean
    @Qualifier("regrasCriacaoSubGrupo")
    public List<RegraCriacaoGrupo> regrasCriacaoSubGrupo(
            RegraProfundidadeMaxima regraProfundidade,
            RegraLimiteDeSubGrupos regraLimiteSubGrupos
    ) {
        return List.of(regraProfundidade, regraLimiteSubGrupos);
    }
}