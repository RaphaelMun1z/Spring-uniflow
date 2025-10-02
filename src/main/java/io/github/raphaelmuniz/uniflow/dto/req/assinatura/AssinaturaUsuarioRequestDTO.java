package io.github.raphaelmuniz.uniflow.dto.req.assinatura;

import io.github.raphaelmuniz.uniflow.dto.req.RequestData;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssinaturaUsuarioRequestDTO implements RequestData<AssinaturaUsuario> {
    private String assinaturaModeloId;
    private String assinanteId;

    public AssinaturaUsuario toModel() {
        return new AssinaturaUsuario(null, null, null, null, null, null, null);
    }
}