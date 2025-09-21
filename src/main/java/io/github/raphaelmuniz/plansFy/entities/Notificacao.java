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
@Table(name = "notificacao", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"titulo", "descricao"})
})
public class Notificacao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "Título não pode ser vazio/nulo")
    private String titulo;

    @NotBlank(message = "Descrição não pode ser vazio/nulo")
    private String descricao;

    @NotNull(message = "Tipo não pode ser nulo")
    private CategoriaNotificacaoEnum tipo;

    @NotBlank(message = "Status não pode ser vazio/nulo")
    private String status;

    @NotNull(message = "Data de criação não pode ser nulo")
    private LocalDateTime dataDeCriacao;

    @NotNull(message = "Data de leitura não pode ser nulo")
    private LocalDateTime dataDeLeitura;

    @NotNull(message = "Tipo de remetente não pode ser nulo")
    private TipoRemetenteEnum remetenteTipo;

    @OneToMany(mappedBy = "notificacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transmissao> transmissoesDeNotificacao;
}