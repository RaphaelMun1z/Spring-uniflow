package io.github.raphaelmuniz.uniflow.dto.req.notificacao;

import io.github.raphaelmuniz.uniflow.dto.req.RequestData;
import io.github.raphaelmuniz.uniflow.entities.notificacao.NotificacaoAssinante;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificacaoAssinanteRequestDTO implements RequestData<NotificacaoAssinante> {
    @NotNull
    private String assinanteId;

    @NotNull
    private String notificacaoId;

    public NotificacaoAssinante toModel() {
        return new NotificacaoAssinante(null, null, null, null, null);
    }
}
