package io.github.raphaelmuniz.uniflow.services.regras.grupo;

import io.github.raphaelmuniz.uniflow.exceptions.models.BusinessException;
import io.github.raphaelmuniz.uniflow.services.regras.grupo.config.ContextoCriacaoGrupo;
import io.github.raphaelmuniz.uniflow.services.regras.grupo.config.RegraCriacaoGrupo;
import org.springframework.stereotype.Component;

@Component
public class RegraProfundidadeMaxima implements RegraCriacaoGrupo {
    @Override
    public void verificar(ContextoCriacaoGrupo contexto) {
        if (contexto.grupoPai().getGrupoPai() != null) {
            throw new BusinessException("Não é permitido aninhar mais de duas camadas de grupos.");
        }
    }
}
