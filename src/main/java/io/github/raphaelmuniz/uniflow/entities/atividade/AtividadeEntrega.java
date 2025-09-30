package io.github.raphaelmuniz.uniflow.entities.atividade;

import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import io.github.raphaelmuniz.uniflow.entities.usuario.Estudante;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "atividade_assinante", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"titulo", "disciplina_id"})
})
public class AtividadeEntrega extends Atividade implements Serializable {
    public AtividadeEntrega(LocalDateTime dataLancamento, LocalDateTime prazoEntrega, String titulo, String descricao, DificuldadeEnum dificuldade, Disciplina disciplina, String id, StatusEntregaEnum statusEntrega, Estudante estudanteDono, AtividadeAvaliativa atividadeAvaliativaOrigem) {
        super(dataLancamento, prazoEntrega, titulo, descricao, dificuldade, disciplina);
        this.id = id;
        this.statusEntrega = statusEntrega;
        this.estudanteDono = estudanteDono;
        this.atividadeAvaliativaOrigem = atividadeAvaliativaOrigem;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull(message = "Status não pode ser nulo")
    @Enumerated(EnumType.STRING)
    private StatusEntregaEnum statusEntrega = StatusEntregaEnum.PENDENTE;

    private String textoResposta;

    @NotNull(message = "Anexos não pode ser nulo")
    private List<String> anexos = new ArrayList<>();

    private LocalDateTime dataEnvio;

    private AvaliacaoAtividade avaliacaoAtividade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudante_dono_id", nullable = false)
    private Estudante estudanteDono;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atividade_avaliativa_origem_id")
    private AtividadeAvaliativa atividadeAvaliativaOrigem = null;
}
