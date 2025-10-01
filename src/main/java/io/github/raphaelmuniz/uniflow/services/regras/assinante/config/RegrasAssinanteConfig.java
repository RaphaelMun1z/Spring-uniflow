package io.github.raphaelmuniz.uniflow.services.regras.assinante.config;

import io.github.raphaelmuniz.uniflow.services.regras.assinante.RegraAssinaturaUnicaAtiva;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RegrasAssinanteConfig {
    @Bean
    @Qualifier("regrasCriacaoAssinatura")
    public List<RegraValidacaoAssinatura> regrasCriacaoAssinatura(RegraAssinaturaUnicaAtiva regraAssinaturaUnicaAtiva) {
        return List.of(regraAssinaturaUnicaAtiva);
    }
}
