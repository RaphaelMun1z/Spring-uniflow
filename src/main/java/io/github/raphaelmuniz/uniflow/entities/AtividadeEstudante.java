package io.github.raphaelmuniz.uniflow.entities;

import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "atividade_assinante", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"titulo", "disciplina_id"})
})
public class AtividadeEstudante extends Atividade implements Serializable {
    public AtividadeEstudante(LocalDateTime dataLancamento, LocalDateTime prazoEntrega, String titulo, String descricao, DificuldadeEnum dificuldade, Disciplina disciplina, String id, StatusEntregaEnum statusEntrega, Estudante estudanteDono, AtividadeGrupo atividadeGrupoOrigem) {
        super(dataLancamento, prazoEntrega, titulo, descricao, dificuldade, disciplina);
        this.id = id;
        this.statusEntrega = statusEntrega;
        this.estudanteDono = estudanteDono;
        this.atividadeGrupoOrigem = atividadeGrupoOrigem;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull(message = "Status não pode ser nulo")
    @Enumerated(EnumType.STRING)
    private StatusEntregaEnum statusEntrega = StatusEntregaEnum.PENDENTE;

    private Double nota = null;

    private String feedback = null;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudante_dono_id", nullable = false)
    private Estudante estudanteDono;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atividade_grupo_origem_id")
    private AtividadeGrupo atividadeGrupoOrigem = null;
}
