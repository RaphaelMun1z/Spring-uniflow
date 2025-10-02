package io.github.raphaelmuniz.uniflow.entities.assinatura;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "assinatura_modelo", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nome", "preco"})
})
public class AssinaturaModelo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "Nome não pode ser vazio/nulo")
    private String nome;

    @NotBlank(message = "Descrição não pode ser vazio/nulo")
    private String descricao;

    @NotNull(message = "Preço não pode ser nulo")
    private BigDecimal preco;

    @NotNull(message = "Duração em meses não pode ser nulo")
    private Integer duracaoEmMeses;

    @NotNull(message = "Limite de Grupos não pode ser nulo")
    private Integer limiteDeGrupos;

    @NotNull(message = "Limite de SubGrupos não pode ser nulo")
    private Integer limiteDeSubGrupos;

    @NotNull(message = "Limite de Membros por Grupo não pode ser nulo")
    private Integer limiteMembrosPorGrupo;

    @NotNull(message = "Permite Analytics não pode ser nulo")
    private Boolean permiteAnalytics;

    @NotNull(message = "Permite Templates de Atividade não pode ser nulo")
    private Boolean permiteTemplatesDeAtividade;

    @NotNull(message = "Permite Criar Subgrupos não pode ser nulo")
    private Boolean permiteCriarSubgrupos;

    @NotNull(message = "Ativo não pode ser nulo")
    private Boolean ativo;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;
}
