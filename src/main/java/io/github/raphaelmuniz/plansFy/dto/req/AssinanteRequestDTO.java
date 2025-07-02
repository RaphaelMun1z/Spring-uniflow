package io.github.raphaelmuniz.plansFy.dto.req;

import io.github.raphaelmuniz.plansFy.entities.AssinaturaUsuario;
import io.github.raphaelmuniz.plansFy.entities.AtividadeCopia;
import io.github.raphaelmuniz.plansFy.entities.InscricaoGrupo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class AssinanteRequestDTO {
    private String nome;
    private String email;
    private String assinaturaModeloId;
    private List<String> atividadesModeloId;
    private List<String> gruposId;
}
