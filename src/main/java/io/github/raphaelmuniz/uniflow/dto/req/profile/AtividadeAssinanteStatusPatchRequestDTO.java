package io.github.raphaelmuniz.uniflow.dto.req.profile;


import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AtividadeAssinanteStatusPatchRequestDTO {
    @NotNull(message = "O status da entrega não pode ser nulo.")
    private StatusEntregaEnum status;
}
