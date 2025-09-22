package io.github.raphaelmuniz.uniflow.dto.res;

import io.github.raphaelmuniz.uniflow.entities.AtividadeCopia;
import io.github.raphaelmuniz.uniflow.entities.Estudante;
import io.github.raphaelmuniz.uniflow.entities.InscricaoGrupo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EstudanteResponseDTO {
    private String id;
    private String nome;
    private String email;
    private Integer periodo;
    private String assinaturaId;
    private List<String> atividadesId;
    private List<String> gruposId;

    public EstudanteResponseDTO(Estudante estudante) {
        this.id = estudante.getId();
        this.nome = estudante.getNome();
        this.email = estudante.getEmail();
        this.periodo = estudante.getPeriodo();
        this.assinaturaId = estudante.getAssinaturaUsuario().getId();
        this.atividadesId = estudante.getAtividades().stream().map(AtividadeCopia::getId).toList();
        this.gruposId = estudante.getGrupos().stream().map(InscricaoGrupo::getId).toList();
    }

    public Estudante toModel() {
        return new Estudante(id, nome, email, null, null, null, periodo);
    }


}
