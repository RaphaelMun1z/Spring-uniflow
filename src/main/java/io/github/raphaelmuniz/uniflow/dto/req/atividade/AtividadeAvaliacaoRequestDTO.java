package io.github.raphaelmuniz.uniflow.dto.req.atividade;

import io.github.raphaelmuniz.uniflow.dto.req.RequestData;
import io.github.raphaelmuniz.uniflow.entities.atividade.AvaliacaoAtividade;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AtividadeAvaliacaoRequestDTO implements RequestData<AvaliacaoAtividade> {
    @NotNull(message = "A nota não pode ser nula.")
    @Min(value = 0, message = "A nota não pode ser menor que 0.")
    @Max(value = 100, message = "A nota não pode ser maior que 100.")
    private Double nota;

    private String feedback;

    @NotNull(message = "Professor Avaliador não pode ser nulo.")
    private String professorAvaliadorId;

    public AvaliacaoAtividade toModel() {
        return new AvaliacaoAtividade(null, null, nota, feedback, null, null);
    }
}
