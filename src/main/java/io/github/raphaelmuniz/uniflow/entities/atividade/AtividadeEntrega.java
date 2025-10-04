package io.github.raphaelmuniz.uniflow.entities.atividade;

import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import io.github.raphaelmuniz.uniflow.entities.usuario.Estudante;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "atividade_entrega")
public class AtividadeEntrega implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull(message = "O status da entrega não pode ser nulo.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEntregaEnum statusEntrega = StatusEntregaEnum.PENDENTE;

    @Lob
    private String textoResposta;

    @ElementCollection
    @CollectionTable(name = "atividade_entrega_anexos", joinColumns = @JoinColumn(name = "atividade_entrega_id"))
    @Column(name = "url_anexo")
    private List<String> anexos = new ArrayList<>();

    private LocalDateTime dataEnvio;

    @OneToOne(mappedBy = "atividadeAvaliada", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    private AvaliacaoAtividade avaliacaoAtividade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudante_dono_id", nullable = false)
    @ToString.Exclude
    private Estudante estudanteDono;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atividade_avaliativa_origem_id", nullable = false) // MELHORIA
    @ToString.Exclude
    private AtividadeAvaliativa atividadeAvaliativaOrigem;

    public void setAvaliacao(AvaliacaoAtividade avaliacao) {
        if (avaliacao != null) {
            avaliacao.setAtividadeAvaliada(this);
        }
        this.avaliacaoAtividade = avaliacao;
    }
}