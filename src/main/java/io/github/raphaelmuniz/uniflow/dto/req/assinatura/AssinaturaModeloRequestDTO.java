package io.github.raphaelmuniz.uniflow.dto.req.assinatura;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record AssinaturaModeloRequestDTO(

        @NotBlank(message = "O nome do plano é obrigatório.")
        String nome,

        @NotBlank(message = "A descrição do plano é obrigatória.")
        String descricao,

        @NotNull(message = "O preço não pode ser nulo.")
        @PositiveOrZero(message = "O preço deve ser zero ou maior.")
        BigDecimal preco,

        @NotNull(message = "A duração em meses é obrigatória.")
        @PositiveOrZero(message = "A duração deve ser zero ou maior.")
        Integer duracaoEmMeses,

        @NotNull(message = "O limite de grupos é obrigatório.")
        @PositiveOrZero(message = "O limite de grupos deve ser zero ou maior.")
        Integer limiteDeGrupos,

        @NotNull(message = "O limite de membros por grupo é obrigatório.")
        @PositiveOrZero(message = "O limite de membros por grupo deve ser zero ou maior.")
        Integer limiteMembrosPorGrupo,

        @NotNull(message = "É obrigatório definir se a criação de subgrupos é permitida.")
        Boolean permiteCriarSubgrupos,

        @NotNull(message = "É obrigatório definir se o uso de templates de atividade é permitido.")
        Boolean permiteTemplatesDeAtividade

) {}