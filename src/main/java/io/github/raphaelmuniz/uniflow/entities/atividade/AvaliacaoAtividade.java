package io.github.raphaelmuniz.uniflow.entities.atividade;

import io.github.raphaelmuniz.uniflow.entities.usuario.Professor;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString(exclude = {"atividadeAvaliada", "professorAvaliador"})
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "avaliacao_atividade")
public class AvaliacaoAtividade {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataAvaliacao;

    @NotNull(message = "A nota não pode ser nula.")
    @Min(value = 0, message = "A nota não pode ser menor que 0.")
    @Max(value = 100, message = "A nota não pode ser maior que 100.")
    @Column(nullable = false)
    private Double nota;

    @Column(columnDefinition = "TEXT")
    private String feedback;

    @NotNull(message = "A atividade avaliada não pode ser nula.")
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "atividade_entrega_id", unique = true, nullable = false)
    private AtividadeEntrega atividadeAvaliada;

    @NotNull(message = "O professor avaliador não pode ser nulo.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_avaliador_id", nullable = false)
    private Professor professorAvaliador;
}