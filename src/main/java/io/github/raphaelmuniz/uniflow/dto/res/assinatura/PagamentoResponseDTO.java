package io.github.raphaelmuniz.uniflow.dto.res.assinatura;

import io.github.raphaelmuniz.uniflow.entities.assinatura.Pagamento;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PagamentoResponseDTO {
    private String id;
    private LocalDateTime dataPagamento;
    private BigDecimal valor;
    private String status;
    private String metodo;
    private String protocolo;
    private String assinanteId;

    public PagamentoResponseDTO(Pagamento pagamento) {
        this.id = pagamento.getId();
        this.dataPagamento = pagamento.getDataPagamento();
        this.valor = pagamento.getValor();
        this.status = pagamento.getStatus();
        this.metodo = pagamento.getMetodo();
        this.protocolo = pagamento.getProtocolo();
        this.assinanteId = pagamento.getAssinantePagador().getId();
    }

    public Pagamento toModel() {
        return new Pagamento(null, dataPagamento, valor, status, metodo, protocolo, null);
    }
}
