package io.github.raphaelmuniz.uniflow.entities.assinatura;

import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(exclude = "assinante")
@AllArgsConstructor
@NoArgsConstructor
public class AssinaturaUsuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull(message = "Assinatura não pode ser nulo")
    private AssinaturaModelo assinaturaModelo;

    @NotNull(message = "Data início não pode ser nulo")
    private LocalDateTime dataInicio;

    @NotNull(message = "Data expiração não pode ser nulo")
    private LocalDateTime dataExpiracao;

    @NotNull(message = "Status não pode ser nulo")
    private Boolean status;

    @NotNull(message = "Assinante não pode ser nulo")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assinante_id", referencedColumnName = "id")
    private Assinante assinante;

    public boolean isVigente() {
        return this.status && this.dataExpiracao.isAfter(LocalDateTime.now());
    }
}
