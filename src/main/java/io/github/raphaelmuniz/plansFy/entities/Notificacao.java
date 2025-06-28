package io.github.raphaelmuniz.plansFy.entities;

import io.github.raphaelmuniz.plansFy.entities.enums.CategoriaNotificacaoEnum;
import io.github.raphaelmuniz.plansFy.entities.enums.TipoRemetenteEnum;
import jakarta.persistence.*;
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
public class Notificacao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String titulo;
    private String descricao;
    private CategoriaNotificacaoEnum tipo;
    private String status;
    private LocalDateTime dataDeCriacao;
    private LocalDateTime dataDeLeitura;
    private TipoRemetenteEnum remetenteTipo;

    @ManyToMany(mappedBy = "notificacoes")
    private List<TransmissaoDeNotificacao> transmissoesDeNotificacao;
}