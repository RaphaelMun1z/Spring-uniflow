package io.github.raphaelmuniz.uniflow.entities.assinatura;

import io.github.raphaelmuniz.uniflow.entities.enums.StatusPlanoEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "assinatura_modelo", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nome"})
})
public class AssinaturaModelo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "O nome do plano é obrigatório.")
    @Column(nullable = false, unique = true)
    private String nome;

    @NotBlank(message = "A descrição do plano é obrigatória.")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @NotNull(message = "O preço não pode ser nulo.")
    @PositiveOrZero(message = "O preço deve ser R$0.00 ou maior.")
    @Column(nullable = false)
    private BigDecimal preco;

    @NotNull(message = "A duração em meses é obrigatória.")
    @Positive(message = "A duração deve ser de no mínimo 1 mês.")
    @Column(nullable = false)
    private Integer duracaoEmMeses;

    @NotNull(message = "O limite de grupos é obrigatório.")
    @PositiveOrZero(message = "O limite de grupos deve ser 0 ou maior.")
    @Column(nullable = false)
    private Integer limiteDeGrupos;

    @NotNull(message = "O limite de subgrupos é obrigatório.")
    @PositiveOrZero(message = "O limite de subgrupos deve ser 0 ou maior.")
    @Column(nullable = false)
    private Integer limiteDeSubGrupos;

    @NotNull(message = "O limite de membros por grupo é obrigatório.")
    @PositiveOrZero(message = "O limite de membros por grupo deve ser 0 ou maior.")
    @Column(nullable = false)
    private Integer limiteMembrosPorGrupo;

    @NotNull(message = "É obrigatório definir se permite analytics.")
    @Column(nullable = false)
    private Boolean permiteAnalytics;

    @NotNull(message = "É obrigatório definir se permite templates de atividade.")
    @Column(nullable = false)
    private Boolean permiteTemplatesDeAtividade;

    @NotNull(message = "É obrigatório definir se permite criar subgrupos.")
    @Column(nullable = false)
    private Boolean permiteCriarSubgrupos;

    @NotNull(message = "O status do plano é obrigatório.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPlanoEnum status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;
}
