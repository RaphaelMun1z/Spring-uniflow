package io.github.raphaelmuniz.plansFy.dto.req;

import io.github.raphaelmuniz.plansFy.entities.AssinaturaModelo;
import io.github.raphaelmuniz.plansFy.entities.AssinaturaUsuario;
import jakarta.validation.constraints.NotNull;
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