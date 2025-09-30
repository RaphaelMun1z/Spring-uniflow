package io.github.raphaelmuniz.uniflow.services.regras.grupo;

import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaModelo;
import io.github.raphaelmuniz.uniflow.entities.grupo.Grupo;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import io.github.raphaelmuniz.uniflow.exceptions.models.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class RegraProfundidadeMaxima implements RegraCriacaoGrupo {
    @Override
    public void verificar(Grupo grupoPai, Assinante criador, AssinaturaModelo plano) {
        if (grupoPai.getGrupoPai() != null) {
            throw new BusinessException("Não é permitido aninhar mais de duas camadas de grupos.");
        }
    }
}
