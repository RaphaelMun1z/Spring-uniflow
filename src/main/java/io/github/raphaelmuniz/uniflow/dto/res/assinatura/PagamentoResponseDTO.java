package io.github.raphaelmuniz.uniflow.dto.res.assinatura;

import io.github.raphaelmuniz.uniflow.entities.assinatura.Pagamento;
import io.github.raphaelmuniz.uniflow.entities.enums.MetodoPagamentoEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusPagamentoEnum;
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
    private StatusPagamentoEnum status;
    private MetodoPagamentoEnum metodo;
    private String protocolo;
    private String assinanteId;

    public PagamentoResponseDTO(Pagamento pagamento) {
        this.id = pagamento.getId();
        this.dataPagamento = pagamento.getDataPagamento();
        this.valor = pagamento.getValor();
        this.status = pagamento.getStatusPagamento();
        this.metodo = pagamento.getMetodoPagamento();
        this.protocolo = pagamento.getProtocolo();
        this.assinanteId = pagamento.getAssinaturaUsuario().getAssinante().getId();
    }

    public Pagamento toModel() {
        return new Pagamento(null, dataPagamento, valor, status, metodo, protocolo, null, null);
    }
}
