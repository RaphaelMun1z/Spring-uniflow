package io.github.raphaelmuniz.plansFy.dto.req;

import io.github.raphaelmuniz.plansFy.entities.AtividadeModelo;
import io.github.raphaelmuniz.plansFy.entities.enums.DificuldadeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AtividadeModeloRequestDTO {
    @NotNull
    private LocalDateTime dataLancamento;

    @NotNull
    private LocalDateTime prazoEntrega;

    @NotBlank
    private String titulo;

    @NotBlank
    private String descricao;

    @NotNull
    private DificuldadeEnum dificuldade;

    @NotBlank
    private String disciplinaId;

    @NotNull
    private Boolean ativa;

    public AtividadeModelo toModel(){
        return new AtividadeModelo(dataLancamento, prazoEntrega, titulo, descricao, dificuldade, null, ativa);
    }
}
