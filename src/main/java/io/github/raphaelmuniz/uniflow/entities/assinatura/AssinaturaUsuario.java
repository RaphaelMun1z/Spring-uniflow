package io.github.raphaelmuniz.uniflow.entities.assinatura;

import io.github.raphaelmuniz.uniflow.entities.enums.StatusAssinaturaUsuarioEnum;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(exclude = "assinante")
@AllArgsConstructor
@NoArgsConstructor
public class AssinaturaUsuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataInicio;

    @NotNull(message = "Data expiração não pode ser nulo")
    private LocalDateTime dataExpiracao;

    @NotNull(message = "Status não pode ser nulo")
    private StatusAssinaturaUsuarioEnum status;

    @NotNull(message = "Assinatura Modelo não pode ser nulo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assinatura_modelo_id")
    private AssinaturaModelo assinaturaModelo;

    @NotNull(message = "Assinante não pode ser nulo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assinante_id", referencedColumnName = "id")
    private Assinante assinante;

    @OneToMany(mappedBy = "assinaturaUsuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pagamento> pagamentos = new ArrayList<>();

    public boolean isVigente() {
        return (this.status.equals(StatusAssinaturaUsuarioEnum.ATIVA) || this.status.equals(StatusAssinaturaUsuarioEnum.EM_TESTE)) && this.dataExpiracao.isAfter(LocalDateTime.now());
    }
}
