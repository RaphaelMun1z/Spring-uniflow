package io.github.raphaelmuniz.uniflow.dto.req.grupo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record SubGrupoRequestDTO(
        @NotBlank(message = "O título não pode ser vazio.")
        String titulo,

        @NotBlank(message = "A descrição não pode ser vazia.")
        String descricao,

        @NotEmpty(message = "É necessário especificar ao menos um membro para o subgrupo.")
        List<String> idsDosMembros
) {}