package io.github.raphaelmuniz.uniflow.entities.assinatura;

import io.github.raphaelmuniz.uniflow.entities.enums.MetodoPagamentoEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusPagamentoEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Data de pagamento não pode ser nulo")
    private LocalDateTime dataPagamento;

    @NotNull(message = "Valor não pode ser nulo")
    @Column(precision = 10, scale = 2)
    private BigDecimal valor;

    @NotNull(message = "Status não pode ser vazio/nulo")
    @Enumerated(EnumType.STRING)
    private StatusPagamentoEnum statusPagamento;

    @NotNull(message = "Metodo não pode ser vazio/nulo")
    @Enumerated(EnumType.STRING)
    private MetodoPagamentoEnum metodoPagamento;

    @NotBlank(message = "Protocolo não pode ser vazio/nulo")
    @Column(unique = true)
    private String protocolo;

    private String idNotaFiscal;

    @NotNull(message = "Assinatura Usuário não pode ser nulo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assinante_usuario_id")
    private AssinaturaUsuario assinaturaUsuario;
}
