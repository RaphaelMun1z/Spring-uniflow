package io.github.raphaelmuniz.uniflow.entities.assinatura;

import io.github.raphaelmuniz.uniflow.entities.enums.MetodoPagamentoEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusPagamentoEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString(exclude = "assinaturaUsuario")
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pagamento")
public class Pagamento implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull(message = "A data do pagamento não pode ser nula.")
    @PastOrPresent(message = "A data do pagamento não pode ser no futuro.")
    @Column(nullable = false)
    private LocalDateTime dataPagamento;

    @NotNull(message = "O valor não pode ser nulo.")
    @Positive(message = "O valor do pagamento deve ser positivo.")
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal valor;

    @NotNull(message = "O status do pagamento é obrigatório.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPagamentoEnum status;

    @NotNull(message = "O método de pagamento é obrigatório.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MetodoPagamentoEnum metodo;

    @NotBlank(message = "O protocolo da transação é obrigatório.")
    @Column(unique = true, nullable = false)
    private String protocolo;

    private String idNotaFiscal;

    @NotNull(message = "A assinatura de usuário não pode ser nula.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assinatura_usuario_id", nullable = false)
    private AssinaturaUsuario assinaturaUsuario;
}
