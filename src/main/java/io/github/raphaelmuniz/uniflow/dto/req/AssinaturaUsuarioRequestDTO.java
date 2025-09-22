package io.github.raphaelmuniz.uniflow.dto.req;

import io.github.raphaelmuniz.uniflow.entities.AssinaturaUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AssinaturaUsuarioRequestDTO {
    private String assinaturaId;
    private LocalDateTime dataInicio;
    private LocalDateTime dataExpiracao;
    private Boolean status;
    private String assinanteId;

    public AssinaturaUsuario toModel() {
        return new AssinaturaUsuario(null, null, dataInicio, dataExpiracao, status, null);
    }
}