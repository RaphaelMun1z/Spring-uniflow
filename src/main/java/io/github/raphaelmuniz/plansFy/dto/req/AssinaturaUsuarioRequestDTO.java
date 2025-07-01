package io.github.raphaelmuniz.plansFy.dto.req;

import io.github.raphaelmuniz.plansFy.entities.AssinaturaModelo;
import io.github.raphaelmuniz.plansFy.entities.AssinaturaUsuario;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class AssinaturaUsuarioRequestDTO {
    private AssinaturaModelo assinatura;
    private LocalDateTime dataInicio;
    private LocalDateTime dataExpiracao;
    private Boolean status;

    public AssinaturaUsuario toModel() {
        return new AssinaturaUsuario(null, assinatura, dataInicio, dataExpiracao, status);
    }
}

