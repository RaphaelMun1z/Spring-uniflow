package io.github.raphaelmuniz.uniflow.dto.req.usuario;

import io.github.raphaelmuniz.uniflow.dto.req.RequestData;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AssinanteRequestDTO implements RequestData<Assinante> {
    private String nome;
    private String email;
    private String assinaturaModeloId;
    private List<String> atividadesModeloId;
    private List<String> gruposId;

    public Assinante toModel() {
        return null;
    }
}
