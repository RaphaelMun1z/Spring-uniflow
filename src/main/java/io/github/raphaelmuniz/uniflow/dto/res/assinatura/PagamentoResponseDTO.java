package io.github.raphaelmuniz.uniflow.dto.res.assinatura;

import io.github.raphaelmuniz.uniflow.entities.enums.MetodoPagamentoEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusPagamentoEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagamentoResponseDTO(
        String id,
        LocalDateTime dataPagamento,
        BigDecimal valor,
        StatusPagamentoEnum status,
        MetodoPagamentoEnum metodo,
        String protocolo,
        String assinanteId
) {
}