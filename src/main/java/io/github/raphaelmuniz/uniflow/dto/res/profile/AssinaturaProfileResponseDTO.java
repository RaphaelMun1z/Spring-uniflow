package io.github.raphaelmuniz.uniflow.dto.res.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.AssinaturaModeloResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaUsuario;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusAssinaturaUsuarioEnum;

import java.time.LocalDateTime;

public record AssinaturaProfileResponseDTO(
        String id,
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        StatusAssinaturaUsuarioEnum status,
        String assinaturaModeloId,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        AssinaturaModeloResponseDTO assinaturaModelo
) {
    public static AssinaturaProfileResponseDTO fromEntity(AssinaturaUsuario assinaturaUsuario) {
        if (assinaturaUsuario == null) {
            return null;
        }
        return new AssinaturaProfileResponseDTO(
                assinaturaUsuario.getId(),
                assinaturaUsuario.getDataInicio(),
                assinaturaUsuario.getDataFim(),
                assinaturaUsuario.getStatus(),
                assinaturaUsuario.getAssinaturaModelo() != null ? assinaturaUsuario.getAssinaturaModelo().getId() : null,
                null
        );
    }

    public AssinaturaProfileResponseDTO withModelo(AssinaturaModeloResponseDTO modelo) {
        return new AssinaturaProfileResponseDTO(
                this.id,
                this.dataInicio,
                this.dataFim,
                this.status,
                this.assinaturaModeloId,
                modelo
        );
    }
}