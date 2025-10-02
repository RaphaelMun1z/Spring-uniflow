package io.github.raphaelmuniz.uniflow.services.validation.grupo.rule;

import io.github.raphaelmuniz.uniflow.entities.enums.TipoGrupoEnum;
import io.github.raphaelmuniz.uniflow.entities.usuario.Professor;
import io.github.raphaelmuniz.uniflow.exceptions.models.BusinessException;
import io.github.raphaelmuniz.uniflow.services.validation.grupo.GrupoCreationRule;
import io.github.raphaelmuniz.uniflow.services.validation.grupo.GrupoValidationContext;
import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;

@Component
@Order(2)
public class TipoGrupoPermissaoRule implements GrupoCreationRule {
    @Override
    public void validate(GrupoValidationContext context) {
        if (context.dto().getTipoGrupo() == TipoGrupoEnum.TURMA) {
            if (!(context.usuario() instanceof Professor)) {
                throw new BusinessException("Apenas professores podem criar grupos do tipo TURMA.");
            }
        }
    }
}