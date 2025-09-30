package io.github.raphaelmuniz.uniflow.entities.atividade;

import io.github.raphaelmuniz.uniflow.entities.usuario.Professor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @NotNull(message = "Avaliador não pode ser nulo.")
    private Professor avaliador;
}
