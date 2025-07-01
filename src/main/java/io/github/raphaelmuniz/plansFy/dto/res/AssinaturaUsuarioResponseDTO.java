package io.github.raphaelmuniz.plansFy.dto.res;

import io.github.raphaelmuniz.plansFy.entities.AssinaturaModelo;
import io.github.raphaelmuniz.plansFy.entities.AssinaturaUsuario;

import java.time.LocalDateTime;

public class AssinaturaUsuarioResponseDTO {
    private String id;
    private AssinaturaModelo assinatura;
    private LocalDateTime dataInicio;
    private LocalDateTime dataExpiracao;
    private Boolean status;

    public AssinaturaUsuarioResponseDTO(AssinaturaUsuario data) {
        this.id = data.getId();
        this.assinatura = data.getAssinatura();
        this.dataInicio = data.getDataInicio();
        this.dataExpiracao = data.getDataExpiracao();
        this.status = data.getStatus();
    }

    public AssinaturaUsuario toModel() {
        return new AssinaturaUsuario(id, assinatura, dataInicio, dataExpiracao, status);
    }
}
