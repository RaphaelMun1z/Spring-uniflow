package io.github.raphaelmuniz.uniflow.dto.req.assinatura;

import io.github.raphaelmuniz.uniflow.dto.req.RequestData;
import io.github.raphaelmuniz.uniflow.entities.assinatura.Pagamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PagamentoRequestDTO implements RequestData<Pagamento> {
    @NotNull
    private LocalDateTime dataPagamento;

    @NotNull
    private BigDecimal valor;

    @NotNull
    private String status;

    @NotNull
    private String metodo;

    @NotBlank
    private String protocolo;

    @NotBlank
    private String assinanteId;

    public Pagamento toModel() {
        return new Pagamento(null, dataPagamento, valor, status, metodo, protocolo, null);
    }
}
