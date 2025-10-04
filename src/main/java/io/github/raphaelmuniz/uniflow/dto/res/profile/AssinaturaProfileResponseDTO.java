package io.github.raphaelmuniz.uniflow.dto.res.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.AssinaturaModeloResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaUsuario;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusAssinaturaUsuarioEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AssinaturaProfileResponseDTO {

    private String id;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private StatusAssinaturaUsuarioEnum status;

    private String assinaturaModeloId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AssinaturaModeloResponseDTO assinaturaModelo;

    public AssinaturaProfileResponseDTO(AssinaturaUsuario assinaturaUsuario) {
        this.id = assinaturaUsuario.getId();
        this.dataInicio = assinaturaUsuario.getDataInicio();
        this.dataFim = assinaturaUsuario.getDataExpiracao();
        this.status = assinaturaUsuario.getStatus();
        if (assinaturaUsuario.getAssinaturaModelo() != null) {
            this.assinaturaModeloId = assinaturaUsuario.getAssinaturaModelo().getId();
        }
    }
}