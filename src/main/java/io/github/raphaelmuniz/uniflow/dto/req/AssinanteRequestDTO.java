package io.github.raphaelmuniz.uniflow.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AssinanteRequestDTO {
    private String nome;
    private String email;
    private String assinaturaModeloId;
    private List<String> atividadesModeloId;
    private List<String> gruposId;
}
