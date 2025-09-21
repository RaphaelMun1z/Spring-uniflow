package io.github.raphaelmuniz.plansFy.entities;

import io.github.raphaelmuniz.plansFy.entities.enums.TipoDestinatarioEnum;
import io.github.raphaelmuniz.plansFy.entities.enums.TipoRemetenteEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transmissao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notificacao_id")
    private Notificacao notificacao;

    @NotNull(message = "Tipo de remetente não pode ser nulo")
    @Enumerated(EnumType.STRING)
    private TipoRemetenteEnum remetenteTipo;

    @NotNull(message = "Tipo de destinatário não pode ser nulo")
    @Enumerated(EnumType.STRING)
    private TipoDestinatarioEnum destinatarioTipo;

    private LocalDateTime dataDeLeitura;

    @NotNull
    private String status;

}