package io.github.raphaelmuniz.uniflow.services.validation;

import io.github.raphaelmuniz.uniflow.exceptions.models.BusinessException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class LimiteDeGruposRule implements GrupoCreationRule {
    @Override
    public void validate(GrupoValidationContext context) {
        Integer limiteDeGrupos = context.plano().getLimiteDeGrupos();

        if (context.contagemDeGruposExistentes() >= limiteDeGrupos) {
            throw new BusinessException(
                    "Você atingiu o limite de %d grupos para o seu plano.".formatted(limiteDeGrupos)
            );
        }
    }
}