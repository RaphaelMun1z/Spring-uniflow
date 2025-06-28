package io.github.raphaelmuniz.plansFy.dto.req;

import io.github.raphaelmuniz.plansFy.entities.AssinaturaUsuario;
import io.github.raphaelmuniz.plansFy.entities.AtividadeCopia;
import io.github.raphaelmuniz.plansFy.entities.Estudante;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EstudanteRequestDTO {
    private String nome;
    private Integer idade;
    private String email;
    private Integer periodo;
    private String assinaturaId;
    private List<String> atividadesId;

    public Estudante toModel() {
        return new Estudante(null, nome, email, null, null, null, periodo);
    }
}
