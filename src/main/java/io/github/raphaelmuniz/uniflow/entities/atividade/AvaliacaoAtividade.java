package io.github.raphaelmuniz.uniflow.entities.atividade;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString(exclude = {"atividadeAvaliada"})
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "avaliacao_atividade")
public class AvaliacaoAtividade {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull(message = "A data da avaliação não pode ser nula.")
    private LocalDateTime dataAvaliacao;

    @NotNull(message = "A nota não pode ser nula.")
    @Min(value = 0, message = "A nota não pode ser menor que 0.")
    @Max(value = 100, message = "A nota não pode ser maior que 100.")
    private Double nota;

    private String feedback;

    @OneToOne(optional = false)
    @JoinColumn(name = "atividade_entrega_id", unique = true)
    private AtividadeEntrega atividadeAvaliada;
}
