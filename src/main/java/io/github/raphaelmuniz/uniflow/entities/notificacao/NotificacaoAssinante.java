package io.github.raphaelmuniz.uniflow.entities.notificacao;

import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"destinatario", "notificacao"})
@Table(name = "notificacao_assinante", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"destinatario_id", "notificacao_id"})
})
public class NotificacaoAssinante implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull
    @Column(nullable = false)
    private boolean lida = false;

    private LocalDateTime dataLeitura;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destinatario_id", nullable = false)
    private Assinante destinatario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notificacao_id", nullable = false)
    private Notificacao notificacao;
}