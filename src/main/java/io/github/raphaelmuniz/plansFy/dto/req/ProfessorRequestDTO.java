package io.github.raphaelmuniz.plansFy.dto.req;

import io.github.raphaelmuniz.plansFy.entities.AssinaturaUsuario;
import io.github.raphaelmuniz.plansFy.entities.AtividadeCopia;
import io.github.raphaelmuniz.plansFy.entities.Grupo;
import io.github.raphaelmuniz.plansFy.entities.Professor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProfessorRequestDTO {
    private String nome;
    private String email;
    private String areaAtuacao;
    private String assinaturaId;
    private List<String> atividadesId;

    public Professor toModel() {
        return new Professor(null, nome, email, null, null, null, areaAtuacao);
    }
}
