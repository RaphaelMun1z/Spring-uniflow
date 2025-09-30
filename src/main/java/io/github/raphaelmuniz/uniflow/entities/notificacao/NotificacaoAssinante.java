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
@ToString(exclude = {"assinanteNotificado", "notificacao"})
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class NotificacaoAssinante implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull
    private Boolean foiLido = false;

    private LocalDateTime dataLeitura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assinante_notificado_id")
    private Assinante assinanteNotificado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notificacao_id")
    private Notificacao notificacao;
}
