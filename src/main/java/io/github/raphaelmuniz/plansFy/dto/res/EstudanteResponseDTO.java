package io.github.raphaelmuniz.plansFy.dto.res;

import io.github.raphaelmuniz.plansFy.entities.AssinaturaUsuario;
import io.github.raphaelmuniz.plansFy.entities.AtividadeCopia;
import io.github.raphaelmuniz.plansFy.entities.Estudante;
import io.github.raphaelmuniz.plansFy.entities.InscricaoGrupo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
