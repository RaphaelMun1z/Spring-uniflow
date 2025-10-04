package io.github.raphaelmuniz.uniflow.entities.atividade;

import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "tarefa_status_membro", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"atividade_colaborativa_id", "membro_id"})
})
public class TarefaStatusMembro implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull(message = "A atividade colaborativa não pode ser nula.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atividade_colaborativa_id", nullable = false)
    @ToString.Exclude
    private AtividadeColaborativa atividadeColaborativa;

    @NotNull(message = "O membro não pode ser nulo.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membro_id", nullable = false)
    @ToString.Exclude
    private Assinante membro;

    @NotNull(message = "O status não pode ser nulo.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEntregaEnum status = StatusEntregaEnum.PENDENTE;

    private LocalDateTime dataConclusao;
}