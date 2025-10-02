package io.github.raphaelmuniz.uniflow.dto.res.profile;

import io.github.raphaelmuniz.uniflow.dto.res.assinatura.AssinaturaModeloResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.AssinaturaUsuarioResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaUsuario;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusAssinaturaUsuarioEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssinaturaProfileResponseDTO {
    private String assinaturaUsuarioId;
    private LocalDateTime dataInicio;
    private LocalDateTime dataExpiracao;
    private StatusAssinaturaUsuarioEnum status;
    private AssinaturaModeloResponseDTO assinaturaModelo;

    public AssinaturaProfileResponseDTO(AssinaturaUsuario assinaturaUsuario) {
        this.assinaturaUsuarioId = assinaturaUsuario.getId();
        this.dataInicio = assinaturaUsuario.getDataInicio();
        this.dataExpiracao = assinaturaUsuario.getDataExpiracao();
        this.status = assinaturaUsuario.getStatus();
        this.assinaturaModelo = null;
    }
}
