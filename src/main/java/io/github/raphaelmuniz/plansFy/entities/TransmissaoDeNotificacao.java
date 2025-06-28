package io.github.raphaelmuniz.plansFy.entities;

import io.github.raphaelmuniz.plansFy.entities.enums.TipoDestinatarioEnum;
import io.github.raphaelmuniz.plansFy.entities.enums.TipoRemetenteEnum;
import jakarta.persistence.*;
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

    private TipoRemetenteEnum remetenteTipo;
    private String remetenteId;
    private TipoDestinatarioEnum destinatarioTipo;
    private String destinatarioId;

    @ManyToMany
    @JoinTable(
            name = "transmissao_notificacao", // nome da tabela intermediária
            joinColumns = @JoinColumn(name = "transmissao_id"),
            inverseJoinColumns = @JoinColumn(name = "notificacao_id")
    )
    private List<Notificacao> notificacoes;
}