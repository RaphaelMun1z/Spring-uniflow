package io.github.raphaelmuniz.uniflow.entities;

import io.github.raphaelmuniz.uniflow.entities.enums.CategoriaNotificacaoEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.TipoRemetenteEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @NotNull(message = "Tipo de remetente não pode ser nulo")
    private TipoRemetenteEnum remetenteTipo;

    @OneToMany(mappedBy = "notificacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transmissao> transmissoesDeNotificacao;

    @OneToMany(mappedBy = "notificacao", cascade = CascadeType.ALL)
    private Set<NotificacaoAssinante> destinatarios = new HashSet<>();

}