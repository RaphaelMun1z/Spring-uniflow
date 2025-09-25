package io.github.raphaelmuniz.uniflow.entities;

import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "atividade_assinante", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"titulo", "disciplina_id"})
})
public class AtividadeAssinante extends Atividade implements Serializable {
    public AtividadeAssinante(LocalDateTime dataLancamento, LocalDateTime prazoEntrega, String titulo, String descricao, DificuldadeEnum dificuldade, Disciplina disciplina, String id, StatusEntregaEnum statusEntrega, Assinante assinanteDono) {
        super(dataLancamento, prazoEntrega, titulo, descricao, dificuldade, disciplina);
        this.id = id;
        this.statusEntrega = statusEntrega;
        this.assinanteDono = assinanteDono;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull(message = "Status não pode ser nulo")
    @Enumerated(EnumType.STRING)
    private StatusEntregaEnum statusEntrega;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assinante_dono_id", nullable = false)
    private Assinante assinanteDono;
}
