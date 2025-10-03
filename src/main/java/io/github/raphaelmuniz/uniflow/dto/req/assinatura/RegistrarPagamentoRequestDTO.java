package io.github.raphaelmuniz.uniflow.dto.req.assinatura;

import io.github.raphaelmuniz.uniflow.entities.enums.MetodoPagamentoEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusPagamentoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RegistrarPagamentoRequestDTO(

        @NotNull(message = "É obrigatório informar a qual assinatura este pagamento pertence.")
        @NotBlank(message = "O ID da assinatura do usuário não pode ser vazio.")
        String assinaturaUsuarioId,

        @NotNull(message = "A data do pagamento é obrigatória.")
        @PastOrPresent(message = "A data do pagamento não pode ser no futuro.")
        LocalDateTime dataPagamento,

        @NotNull(message = "O valor do pagamento é obrigatório.")
        @Positive(message = "O valor do pagamento deve ser positivo.")
        BigDecimal valor,

        @NotNull(message = "O status do pagamento é obrigatório.")
        StatusPagamentoEnum status,

        @NotNull(message = "O método de pagamento é obrigatório.")
        MetodoPagamentoEnum metodo,

        @NotBlank(message = "O protocolo da transação é obrigatório.")
        String protocolo
) {
}