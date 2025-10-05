package io.github.raphaelmuniz.uniflow.dto.req.atividade;

import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record AtividadeColaborativaUpdateRequestDTO(
    @Size(min = 1, message = "O título, se fornecido, não pode ser vazio.")
    String titulo,

    @Size(min = 1, message = "A descrição, se fornecida, não pode ser vazia.")
    String descricao,

    @Future(message = "O prazo de entrega deve ser uma data no futuro.")
    LocalDateTime prazoEntrega,

    DificuldadeEnum dificuldade
) {}