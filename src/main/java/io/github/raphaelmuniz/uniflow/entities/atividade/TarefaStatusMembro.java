package io.github.raphaelmuniz.uniflow.entities.atividade;

import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import io.github.raphaelmuniz.uniflow.entities.usuario.Estudante;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "tarefa_status_membro")
public class TarefaStatusMembro implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull(message = "A atividade colaborativa não pode ser nula")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atividade_colaborativa_id")
    private AtividadeColaborativa atividadeColaborativa;

    @NotNull(message = "O estudante não pode ser nulo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudante_dono_id")
    private Estudante estudanteDono;

    @NotNull(message = "Status não pode ser nulo")
    @Enumerated(EnumType.STRING)
    private StatusEntregaEnum statusEntrega = StatusEntregaEnum.PENDENTE;

    private LocalDateTime dataConclusao;
}
