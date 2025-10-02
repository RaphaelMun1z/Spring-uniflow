package io.github.raphaelmuniz.uniflow.services.regras.grupo.config;

import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaModelo;
import io.github.raphaelmuniz.uniflow.entities.grupo.Grupo;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;

public record ContextoCriacaoGrupo(
        Grupo grupoPai,
        Assinante criador,
        AssinaturaModelo planoDoCriador
) {
}