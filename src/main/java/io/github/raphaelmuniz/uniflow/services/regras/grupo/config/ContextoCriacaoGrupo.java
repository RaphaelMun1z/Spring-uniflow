package io.github.raphaelmuniz.uniflow.services.regras.grupo.config;

import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaModelo;
import io.github.raphaelmuniz.uniflow.entities.grupo.Grupo;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class ContextoCriacaoGrupo {
    private final Grupo grupoPai;
    private final Assinante criador;
    private final AssinaturaModelo planoDoCriador;
}