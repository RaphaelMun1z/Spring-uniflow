package io.github.raphaelmuniz.plansFy.dto.req;

import io.github.raphaelmuniz.plansFy.entities.AssinaturaUsuario;
import io.github.raphaelmuniz.plansFy.entities.AtividadeCopia;
import io.github.raphaelmuniz.plansFy.entities.Estudante;
import io.github.raphaelmuniz.plansFy.entities.InscricaoGrupo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class EstudanteRequestDTO {
    @NotBlank
    private String nome;

    @NotNull
    private Integer idade;

    @NotBlank
    private String email;

    @NotNull
    private Integer periodo;

    @NotBlank
    private String assinaturaId;

    @NotNull
    private List<String> atividadesId;

    @NotNull
    private List<String> gruposId;

    public Estudante toModel() {
        return new Estudante(null, nome, email, null, null, null, periodo);
    }
}
