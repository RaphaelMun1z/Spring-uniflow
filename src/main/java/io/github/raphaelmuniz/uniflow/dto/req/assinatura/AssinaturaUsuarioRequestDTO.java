package io.github.raphaelmuniz.uniflow.dto.req.assinatura;

import io.github.raphaelmuniz.uniflow.dto.req.RequestData;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaUsuario;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusAssinaturaUsuarioEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AssinaturaUsuarioRequestDTO implements RequestData<AssinaturaUsuario> {
    private String assinaturaModeloId;
    private String assinanteId;
    private StatusAssinaturaUsuarioEnum statusAssinaturaUsuario;

    public AssinaturaUsuario toModel() {
        return new AssinaturaUsuario(null, null, null, statusAssinaturaUsuario, null, null, null);
    }
}