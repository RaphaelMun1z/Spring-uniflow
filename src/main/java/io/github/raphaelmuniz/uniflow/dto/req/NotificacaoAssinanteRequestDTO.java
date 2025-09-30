package io.github.raphaelmuniz.uniflow.dto.req;

import io.github.raphaelmuniz.uniflow.entities.Notificacao;
import io.github.raphaelmuniz.uniflow.entities.NotificacaoAssinante;
import io.github.raphaelmuniz.uniflow.entities.enums.CategoriaNotificacaoEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.TipoRemetenteEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

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
