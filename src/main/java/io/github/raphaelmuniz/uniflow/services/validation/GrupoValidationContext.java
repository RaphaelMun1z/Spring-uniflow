package io.github.raphaelmuniz.uniflow.services.validation;

import io.github.raphaelmuniz.uniflow.dto.req.grupo.GrupoRequestDTO;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaModelo;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;

public record GrupoValidationContext(
        Usuario usuario,
        GrupoRequestDTO dto,
        AssinaturaModelo plano,
        long contagemDeGruposExistentes
) {}