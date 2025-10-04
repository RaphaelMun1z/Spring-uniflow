package io.github.raphaelmuniz.uniflow.dto.res.assinatura;

import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaUsuario;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusAssinaturaUsuarioEnum;

import java.time.LocalDateTime;

public record AssinaturaUsuarioResponseDTO(
        String id,
        String assinaturaModeloId,
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        StatusAssinaturaUsuarioEnum status,
        String assinanteId
) {
    public static AssinaturaUsuarioResponseDTO fromEntity(AssinaturaUsuario assinaturaUsuario) {
        if (assinaturaUsuario == null) {
            return null;
        }
        return new AssinaturaUsuarioResponseDTO(
                assinaturaUsuario.getId(),
                assinaturaUsuario.getAssinaturaModelo().getId(),
                assinaturaUsuario.getDataInicio(),
                assinaturaUsuario.getDataFim(),
                assinaturaUsuario.getStatus(),
                assinaturaUsuario.getAssinante().getId()
        );
    }
}