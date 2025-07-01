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
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransmissaoDeNotificacao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull(message = "Tipo de remetente não pode ser nulo")
    private TipoRemetenteEnum remetenteTipo;

    @NotBlank(message = "Remetente Id não pode ser vazio/nulo")
    private String remetenteId;

    @NotNull(message = "Tipo de destinatário não pode ser nulo")
    private TipoDestinatarioEnum destinatarioTipo;

    @NotBlank(message = "Destinatário Id não pode ser vazio/nulo")
    private String destinatarioId;

    @NotNull(message = "Notificações não pode ser nulo")
    @ManyToMany
    @JoinTable(
            name = "transmissao_notificacao", // nome da tabela intermediária
            joinColumns = @JoinColumn(name = "transmissao_id"),
            inverseJoinColumns = @JoinColumn(name = "notificacao_id")
    )
    private List<Notificacao> notificacoes;
}