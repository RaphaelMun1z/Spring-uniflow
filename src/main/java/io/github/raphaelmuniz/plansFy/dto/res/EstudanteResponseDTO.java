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

@Data
@AllArgsConstructor
public class EstudanteResponseDTO {
    private String id;
    private String nome;
    private String email;
    private Integer periodo;
    private AssinaturaUsuario assinatura;
    private List<AtividadeCopia> atividades;
    private Set<InscricaoGrupo> grupos;

    public EstudanteResponseDTO(Estudante estudante) {
        this.id = estudante.getId();
        this.nome = estudante.getNome();
        this.email = estudante.getEmail();
        this.periodo = estudante.getPeriodo();
        this.assinatura = estudante.getAssinatura();
        this.atividades = estudante.getAtividades();
    }

    public Estudante toModel() {
        return new Estudante(id, nome, email, assinatura, atividades, grupos, periodo);
    }
}
