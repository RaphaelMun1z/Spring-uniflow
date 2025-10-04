package io.github.raphaelmuniz.uniflow.dto.req.grupo;

import io.github.raphaelmuniz.uniflow.entities.enums.TipoGrupoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record GrupoRequestDTO(
        @NotBlank(message = "O título do grupo é obrigatório.")
        String titulo,

        @NotBlank(message = "A descrição do grupo é obrigatória.")
        String descricao,

        @NotNull(message = "O tipo do grupo (TURMA ou GRUPO_ESTUDO) é obrigatório.")
        TipoGrupoEnum tipoGrupo,

        @NotEmpty(message = "É necessário fornecer o ID de pelo menos um estudante para inscrever no grupo.")
        List<String> idsEstudantesInscritos,

        @NotBlank(message = "O ID da disciplina é obrigatório.")
        String disciplinaId
) {
}