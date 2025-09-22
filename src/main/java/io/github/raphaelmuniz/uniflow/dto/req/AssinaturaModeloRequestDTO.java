package io.github.raphaelmuniz.uniflow.dto.req;

import io.github.raphaelmuniz.uniflow.entities.AssinaturaModelo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AssinaturaModeloRequestDTO {
    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    @NotNull
    private BigDecimal preco;

    @NotNull
    private Integer duracaoEmMeses;

    @NotNull
    private Boolean ativo;

    @NotNull
    private LocalDateTime dataCriacao;

    @NotNull
    private LocalDateTime dataAtualizacao;

    public AssinaturaModelo toModel() {
        return new AssinaturaModelo(null, nome, descricao, preco, duracaoEmMeses, ativo, dataCriacao, dataAtualizacao);
    }
}
