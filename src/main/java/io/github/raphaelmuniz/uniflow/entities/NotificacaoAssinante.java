package io.github.raphaelmuniz.uniflow.entities;

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
public class NotificacaoAssinante implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull
    private Boolean foiLido = false;

    private LocalDateTime dataLeitura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assinante_id")
    private Assinante assinante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notificacao_id")
    private Notificacao notificacao;
}
