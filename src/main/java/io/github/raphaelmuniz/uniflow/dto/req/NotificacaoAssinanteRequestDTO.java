package io.github.raphaelmuniz.uniflow.dto.req;

import io.github.raphaelmuniz.uniflow.entities.NotificacaoAssinante;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificacaoAssinanteRequestDTO {
    @NotNull
    private String assinanteId;

    @NotNull
    private String notificacaoId;

    public NotificacaoAssinante toModel() {
        return new NotificacaoAssinante(null, null, null, null, null);
    }
}
