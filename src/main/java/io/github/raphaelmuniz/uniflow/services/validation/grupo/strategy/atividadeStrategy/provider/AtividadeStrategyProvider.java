package io.github.raphaelmuniz.uniflow.services.validation.grupo.strategy.atividadeStrategy.provider;

import io.github.raphaelmuniz.uniflow.entities.enums.TipoGrupoEnum;
import io.github.raphaelmuniz.uniflow.services.validation.grupo.strategy.atividadeStrategy.AtividadeStrategy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class AtividadeStrategyProvider {
    private final Map<TipoGrupoEnum, AtividadeStrategy> strategies;

    public AtividadeStrategyProvider(List<AtividadeStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(AtividadeStrategy::supports, Function.identity()));
    }

    public AtividadeStrategy getStrategy(TipoGrupoEnum tipoGrupo) {
        AtividadeStrategy strategy = strategies.get(tipoGrupo);
        if (strategy == null) {
            throw new UnsupportedOperationException("Não há estratégia definida para o tipo de grupo: " + tipoGrupo);
        }
        return strategy;
    }
}