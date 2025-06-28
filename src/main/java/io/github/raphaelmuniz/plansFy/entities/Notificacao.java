package io.github.raphaelmuniz.plansFy.entities;

import io.github.raphaelmuniz.plansFy.entities.enums.CategoriaNotificacaoEnum;
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
public class Notificacao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank
    private String titulo;

    @NotBlank
    private String descricao;

    @NotNull
    private CategoriaNotificacaoEnum tipo;

    @NotNull
    private String status;

    @NotNull
    private LocalDateTime dataDeCriacao;

    @NotNull
    private LocalDateTime dataDeLeitura;

    @NotNull
    private TipoRemetenteEnum remetenteTipo;

    @ManyToMany(mappedBy = "notificacoes")
    private List<TransmissaoDeNotificacao> transmissoesDeNotificacao;
}