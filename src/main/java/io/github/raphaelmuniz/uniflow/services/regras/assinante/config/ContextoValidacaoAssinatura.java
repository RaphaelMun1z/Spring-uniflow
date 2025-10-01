package io.github.raphaelmuniz.uniflow.services.regras.assinante.config;

import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaModelo;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;

public record ContextoValidacaoAssinatura(
        Assinante assinante,
        AssinaturaModelo assinaturaModelo
) {
}