package io.github.raphaelmuniz.uniflow.dto.req;

import io.github.raphaelmuniz.uniflow.entities.Pagamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PagamentoRequestDTO {
    @NotNull
    private LocalDateTime dataPagamento;

    @NotNull
    private Double valor;

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
