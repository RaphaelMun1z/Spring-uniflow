package io.github.raphaelmuniz.uniflow.services.validation.grupo.strategy.listarAtividadesStrategy.provider;

import io.github.raphaelmuniz.uniflow.entities.enums.TipoGrupoEnum;
import io.github.raphaelmuniz.uniflow.services.validation.grupo.strategy.listarAtividadesStrategy.ListarAtividadesStrategy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ListarAtividadesStrategyProvider {
    private final Map<TipoGrupoEnum, ListarAtividadesStrategy> strategies;

    public ListarAtividadesStrategyProvider(List<ListarAtividadesStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(ListarAtividadesStrategy::supports, Function.identity()));
    }

    public ListarAtividadesStrategy getStrategy(TipoGrupoEnum tipoGrupo) {
        ListarAtividadesStrategy strategy = strategies.get(tipoGrupo);
        if (strategy == null) {
            throw new UnsupportedOperationException("Não há estratégia de listagem de atividades definida para o tipo de grupo: " + tipoGrupo);
        }
        return strategy;
    }
}