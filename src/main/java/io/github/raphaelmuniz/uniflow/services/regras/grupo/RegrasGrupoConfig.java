package io.github.raphaelmuniz.uniflow.services.regras.grupo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RegrasGrupoConfig {
    @Bean
    @Qualifier("regrasGrupoPadrao")
    public List<RegraCriacaoGrupo> regrasGrupoPadrao(RegraLimiteDeGruposGeral regra1) {
        return List.of(regra1);
    }

    @Bean
    @Qualifier("regrasSubGrupo")
    public List<RegraCriacaoGrupo> regrasSubGrupo(RegraProfundidadeMaxima regra1, RegraLimiteDeSubGrupos regra2) {
        return List.of(regra1, regra2);
    }
}