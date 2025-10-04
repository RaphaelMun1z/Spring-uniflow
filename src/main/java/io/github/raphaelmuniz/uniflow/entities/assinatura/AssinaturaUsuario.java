package io.github.raphaelmuniz.uniflow.entities.assinatura;

import io.github.raphaelmuniz.uniflow.entities.enums.StatusAssinaturaUsuarioEnum;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "assinatura_usuario")
public class AssinaturaUsuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataInicio;

    @NotNull(message = "A data de fim não pode ser nula.")
    @Column(nullable = false)
    private LocalDateTime dataFim;

    @NotNull(message = "O status não pode ser nulo.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAssinaturaUsuarioEnum status;

    @NotNull(message = "O modelo de assinatura não pode ser nulo.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assinatura_modelo_id", nullable = false)
    @ToString.Exclude
    private AssinaturaModelo assinaturaModelo;

    @NotNull(message = "O assinante não pode ser nulo.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assinante_id", nullable = false)
    @ToString.Exclude
    private Assinante assinante;

    @OneToMany(mappedBy = "assinaturaUsuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Pagamento> pagamentos = new ArrayList<>();

    public boolean isVigente() {
        boolean statusValido = (this.status == StatusAssinaturaUsuarioEnum.ATIVA || this.status == StatusAssinaturaUsuarioEnum.EM_TESTE);
        boolean dentroDaValidade = this.dataFim.isAfter(LocalDateTime.now());
        return statusValido && dentroDaValidade;
    }
}