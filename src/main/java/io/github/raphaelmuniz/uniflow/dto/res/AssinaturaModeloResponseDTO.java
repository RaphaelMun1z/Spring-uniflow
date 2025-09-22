package io.github.raphaelmuniz.uniflow.dto.res;

import io.github.raphaelmuniz.uniflow.entities.AssinaturaModelo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssinaturaModeloResponseDTO {
    private String id;

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

    public AssinaturaModeloResponseDTO(AssinaturaModelo assinaturaModelo) {
        this.id = assinaturaModelo.getId();
        this.nome = assinaturaModelo.getNome();
        this.descricao = assinaturaModelo.getDescricao();
        this.preco = assinaturaModelo.getPreco();
        this.duracaoEmMeses = assinaturaModelo.getDuracaoEmMeses();
        this.ativo = assinaturaModelo.getAtivo();
        this.dataCriacao = assinaturaModelo.getDataCriacao();
        this.dataAtualizacao = assinaturaModelo.getDataAtualizacao();
    }

    public AssinaturaModelo toModel() {
        return new AssinaturaModelo(id, nome, descricao, preco, duracaoEmMeses, ativo, dataCriacao, dataAtualizacao);
    }
}
