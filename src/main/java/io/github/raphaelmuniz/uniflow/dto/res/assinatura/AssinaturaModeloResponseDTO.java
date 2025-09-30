package io.github.raphaelmuniz.uniflow.dto.res.assinatura;

import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaModelo;
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
    private Integer limiteDeGrupos;

    @NotNull
    private Integer limiteMembrosPorGrupo;

    @NotNull
    private Boolean permiteAnalytics;

    @NotNull
    private Boolean permiteTemplatesDeAtividade;

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
        this.limiteDeGrupos = assinaturaModelo.getLimiteDeGrupos();
        this.limiteMembrosPorGrupo = assinaturaModelo.getLimiteMembrosPorGrupo();
        this.permiteAnalytics = assinaturaModelo.getPermiteAnalytics();
        this.permiteTemplatesDeAtividade = assinaturaModelo.getPermiteTemplatesDeAtividade();
        this.preco = assinaturaModelo.getPreco();
        this.duracaoEmMeses = assinaturaModelo.getDuracaoEmMeses();
        this.ativo = assinaturaModelo.getAtivo();
        this.dataCriacao = assinaturaModelo.getDataCriacao();
        this.dataAtualizacao = assinaturaModelo.getDataAtualizacao();
    }

    public AssinaturaModelo toModel() {
        return new AssinaturaModelo(id, nome, descricao, preco, duracaoEmMeses, limiteDeGrupos, limiteMembrosPorGrupo, permiteAnalytics, permiteTemplatesDeAtividade, ativo, dataCriacao, dataAtualizacao);
    }
}
