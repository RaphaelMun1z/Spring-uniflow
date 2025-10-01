package io.github.raphaelmuniz.uniflow.dto.res.assinatura;

import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaUsuario;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusAssinaturaUsuarioEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssinaturaUsuarioResponseDTO {
    private String id;
    private String assinaturaModeloId;
    private LocalDateTime dataInicio;
    private LocalDateTime dataExpiracao;
    private StatusAssinaturaUsuarioEnum status;
    private String assinanteId;

    public AssinaturaUsuarioResponseDTO(AssinaturaUsuario data) {
        this.id = data.getId();
        this.assinaturaModeloId = data.getAssinaturaModelo().getId();
        this.dataInicio = data.getDataInicio();
        this.dataExpiracao = data.getDataExpiracao();
        this.status = data.getStatus();
        this.assinanteId = data.getAssinante().getId();
    }

    public AssinaturaUsuario toModel() {
        return new AssinaturaUsuario(id, dataInicio, dataExpiracao, status, null, null, null);
    }
}
