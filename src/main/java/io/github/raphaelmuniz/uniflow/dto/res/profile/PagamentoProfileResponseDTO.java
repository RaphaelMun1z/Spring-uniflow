package io.github.raphaelmuniz.uniflow.dto.res.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoProfileResponseDTO {
    private String id;
    private LocalDateTime dataPagamento;
    private BigDecimal valor;
    private String status;
    private String metodo;
    private String protocolo;
    private String assinanteId;
}
