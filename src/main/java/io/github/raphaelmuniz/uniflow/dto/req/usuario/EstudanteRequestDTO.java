package io.github.raphaelmuniz.uniflow.dto.req.usuario;

import io.github.raphaelmuniz.uniflow.dto.req.RequestData;
import io.github.raphaelmuniz.uniflow.entities.usuario.Estudante;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EstudanteRequestDTO implements RequestData<Estudante> {
    @NotBlank
    private String nome;

    @NotNull
    private Integer idade;

    @NotBlank
    private String email;

    @NotBlank
    private String senha;

    @NotNull
    private Integer periodo;

    @NotBlank
    private String assinaturaId;

    @NotNull
    private List<String> gruposId;

    public Estudante toModel() {
        return new Estudante(nome, email, senha, null, null, null, null, null, null, null, this.periodo, null);
    }
}
