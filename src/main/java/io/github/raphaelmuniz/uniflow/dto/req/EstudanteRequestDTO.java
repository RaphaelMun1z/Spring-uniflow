package io.github.raphaelmuniz.uniflow.dto.req;

import io.github.raphaelmuniz.uniflow.entities.Estudante;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

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
    private List<String> gruposId;

    public Estudante toModel() {
        return new Estudante(null, nome, email, null, null, null, null, null, null, null, null, this.periodo);
    }
}
