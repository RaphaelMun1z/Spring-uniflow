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
    @NotBlank(message = "O nome da assinatura não pode ser vazio ou nulo.")
    private String nome;

    @NotBlank(message = "A descrição da assinatura não pode ser vazia ou nula.")
    private String descricao;

    @NotNull(message = "O preço não pode ser nulo.")
    private BigDecimal preco;

    @NotNull(message = "A duração em meses não pode ser nula.")
    private Integer duracaoEmMeses;

    @NotNull(message = "O limite de grupos não pode ser nulo.")
    private Integer limiteDeGrupos;

    @NotNull(message = "O limite de membros por grupo não pode ser nulo.")
    private Integer limiteMembrosPorGrupo;

    @NotNull(message = "A permissão de analytics não pode ser nula.")
    private Boolean permiteAnalytics;

    @NotNull(message = "A permissão de templates de atividade não pode ser nula.")
    private Boolean permiteTemplatesDeAtividade;

    @NotNull(message = "O status 'ativo' não pode ser nulo.")
    private Boolean ativo;

    @NotNull(message = "A data de criação não pode ser nula.")
    private LocalDateTime dataCriacao;

    @NotNull(message = "A data de atualização não pode ser nula.")
    private LocalDateTime dataAtualizacao;

    public AssinaturaModelo toModel() {
        return new AssinaturaModelo(null, nome, descricao, preco, duracaoEmMeses, limiteDeGrupos, limiteMembrosPorGrupo, permiteAnalytics, permiteTemplatesDeAtividade, ativo, dataCriacao, dataAtualizacao);
    }
}
