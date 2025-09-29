package io.github.raphaelmuniz.uniflow.dto.req.profile;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AtividadeAvaliacaoRequestDTO {
    @NotNull(message = "A nota não pode ser nula.")
    @Min(value = 0, message = "A nota não pode ser menor que 0.")
    @Max(value = 100, message = "A nota não pode ser maior que 100.")
    private Double nota;

    @NotBlank(message = "O feedback não pode ser vazio.")
    private String feedback;
}
